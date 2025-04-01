let blocklist = {};
blocklist.common = {};
blocklist.common.pws_option_val = "off";

console.log('[RogueKiller] Service worker started');
blocklist.common.GET_BLOCKLIST = 'getBlocklist';
blocklist.common.ADD_TO_BLOCKLIST = 'addToBlocklist';
blocklist.common.ADD_LIST_TO_BLOCKLIST = 'addListToBlocklist';
blocklist.common.DELETE_FROM_BLOCKLIST = 'deleteFromBlocklist';
blocklist.common.GET_PWS_OPTION_VAL = "getPwsOptionVal";
blocklist.common.CHANGE_PWS_OPTION_VAL = "changePwsOptionVal";
blocklist.common.UPDATE="updateBlocklist"
blocklist.common.FETCH = "fetch"
blocklist.common.HOST_REGEX = new RegExp(
  '^https?://(www[.])?([0-9a-zA-Z.-]+).*$');

// 初始化存储
chrome.storage.local.get(['blocklist', 'blocklist_pws_option'], function(result) {
  if (!result.blocklist) {
    chrome.storage.local.set({blocklist: []});
  }
  if (!result.blocklist_pws_option) {
    chrome.storage.local.set({blocklist_pws_option: "off"});
  }
});

//启动黑名单监听
blocklist.common.startBackgroundListeners = function () {
  chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
      if (request.type == blocklist.common.GET_BLOCKLIST) {
        chrome.storage.local.get(['blocklist', 'blocklist_pws_option'], function(result) {
          if (chrome.runtime.lastError) {
            console.error('Error getting blocklist:', chrome.runtime.lastError);
            sendResponse({
              blocklist: [],
              pws_option: "off"
            });
          } else {
            sendResponse({
              blocklist: result.blocklist || [],
              pws_option: result.blocklist_pws_option || "off"
            });
          }
        });
        return true;
        /* 检查本地存储中是否有 blocklist 这个键，如果没有，就把 blocklistPatterns 这个变量的值转换成 JSON 格式并存储在本地存储中；
        如果有，就把本地存储中的 blocklist 的值解析成 JavaScript 对象并赋给 blocklistPatterns 这个变量 */
        sendResponse({
          blocklist: blocklistPatterns
        });//发送回复 一个json对象
      } else if (request.type == blocklist.common.ADD_TO_BLOCKLIST) {
        chrome.storage.local.get(['blocklist'], function(result) {
          let blocklists = result.blocklist || [];
          if (blocklists.indexOf(request.pattern) == -1) {
            blocklists.push(request.pattern);
            blocklists.sort();
            chrome.storage.local.set({blocklist: blocklists}, function() {
              sendResponse({
                success: 1,
                pattern: request.pattern
              });
            });
          } else {
            sendResponse({
              success: 1,
              pattern: request.pattern
            });
          }
        });
        return true;
      /* 接收一个请求对象，然后把请求对象中的 pattern 属性的值添加到本地存储中的 blocklist 这个键对应的数组中，
      如果数组中没有这个值的话。然后把数组按字母顺序排序，并转换成 JSON 格式存储在本地存储中。
      最后发送一个响应对象，包含 success 和 pattern 两个属性。 */
      
      //这里表示使用列表添加黑名单
      } else if (request.type == blocklist.common.ADD_LIST_TO_BLOCKLIST) {
        let regex = /(https?:\/\/)?(www[.])?([0-9a-zA-Z.-]+).*(\r\n|\n)?/g;
        let arr = [];
        while ((m = regex.exec(request.pattern)) !== null) {
          arr.push(m[3]);
        }
        chrome.storage.local.get(['blocklist'], function(result) {
          let blocklists = result.blocklist || [];
          for (let i = 0, length = arr.length; i < length; i++) {
            if (blocklists.indexOf(arr[i]) == -1) {
              blocklists.push(arr[i]);
            }
          }
          blocklists.sort();
          chrome.storage.local.set({blocklist: blocklists}, function() {
            sendResponse({
              success: 1,
              pattern: request.pattern
            });
          });
        });
        return true;
         //使用 JSON.stringify() 方法将 JavaScript 对象转换为字符串。
         //移除黑名单
      } else if (request.type == blocklist.common.DELETE_FROM_BLOCKLIST) {
        chrome.storage.local.get(['blocklist'], function(result) {
          let blocklists = result.blocklist || [];
          let index = blocklists.indexOf(request.pattern);
          if (index != -1) {
            blocklists.splice(index, 1);
            chrome.storage.local.set({blocklist: blocklists}, function() {
              blocklist.common.updateDynamicRules();
              sendResponse({
                pattern: request.pattern,
                success: 1
              });
            });
          } else {
            sendResponse({
              pattern: request.pattern,
              success: 0
            });
          }
        });
        return true;
        /* 接收一个请求对象，然后检查本地存储中是否有 blocklist_pws_option 这个键，如果没有，就把它的值设为 “off”。
        然后发送一个响应对象，包含 pws_option 这个属性，它的值就是本地存储中的 blocklist_pws_option 的值。 */
      } else if (request.type == blocklist.common.GET_PWS_OPTION_VAL) {
        chrome.storage.local.get(['blocklist_pws_option'], function(result) {
          let pws_option = result.blocklist_pws_option || "off";
          sendResponse({
            pws_option: pws_option
          });
        });
        return true;

        /* 接收一个请求对象，然后把本地存储中的 blocklist_pws_option 这个键的值设为请求对象中的 val 属性的值。
        然后发送一个响应对象，包含 pws_option 这个属性，它的值就是请求对象中的 val 属性的值。
        感觉这两个部分是和什么时候不屏蔽有关的，具体再看看后面的语法 */
      } else if (request.type == blocklist.common.CHANGE_PWS_OPTION_VAL) {
        chrome.storage.local.set({blocklist_pws_option: request.val}, function() {
          sendResponse({
            pws_option: request.val
          });
        });
        return true;
        /* 更新url模块 */
      } else if(request.type == blocklist.common.UPDATE){
        fetch("http://jsonplaceholder.typicode.com/posts")
          .then(response => response.text())
          .then(text => {
            alert("已成功同步Rogue killer最新黑名单");
            let regex = /(https?:\/\/)?(www[.])?([0-9a-zA-Z.-]+).*(\s)?/g;
            let arr = [];
            while ((m = regex.exec(text)) !== null) {
              arr.push(m[3]);
            }
            chrome.storage.local.get(['blocklist'], function(result) {
              let blocklists = result.blocklist || [];
              for (let i = 0, length = arr.length; i < length; i++) {
                if (blocklists.indexOf(arr[i]) == -1) {
                  blocklists.push(arr[i]);
                }
              }
              blocklists.sort();
              chrome.storage.local.set({blocklist: blocklists});
            });
          })
          .catch(error => console.error('Error:', error));
      }
      else if (request.type == blocklist.common.FETCH) {
        (async () => {
          try {
            const res = await fetch(request.urls, {
              headers: {
                'Content-Type': 'text/plain'
              },
              mode: 'cors'
            });
            const url = JSON.stringify(res.url);
            sendResponse({
              url: url
            });
          } catch (error) {
            console.error('Fetch error:', error);
            sendResponse({
              error: error.message
            });
          }
        })();
        return true;
      }
    }
  )

};

//获取根据url获得hostname  用正则表达式blocklist.common.HOST_REGEX匹配pattern，并返回匹配结果的第二个分组
blocklist.common.getHostNameFromUrl = function (pattern) {
  return pattern.replace(blocklist.common.HOST_REGEX, '$2');
}

//本身就是个监听函数，前面定义，这里启动
blocklist.common.startBackgroundListeners();


// 验证黑名单数据加载
chrome.storage.local.get(['blocklist'], function(result) {
  console.debug('[RogueKiller] 当前黑名单数据:', {
    count: result.blocklist ? result.blocklist.length : 0,
    sample: result.blocklist ? result.blocklist.slice(0, 3) : []
  });
});

// 更新动态规则
blocklist.common.updateDynamicRules = async function() {
  console.log('[RogueKiller] 更新动态规则');
  const result = await chrome.storage.local.get(['blocklist', 'blocklist_pws_option']);
  const blocklists = result.blocklist || [];
  const pwsOption = result.blocklist_pws_option || "off";

  const rules = blocklists.map((domain, index) => ({
    id: index + 1,
    priority: 1,
    action: {
      type: pwsOption === "on" ? 'allow' : 'block'
    },
    condition: {
      urlFilter: `||${domain}^`,
      resourceTypes: ['main_frame']
    }
  }));

  try {
    // 获取当前所有规则ID
    const currentRules = await chrome.declarativeNetRequest.getDynamicRules();
    const currentRuleIds = currentRules.map(rule => rule.id);
    
    await chrome.declarativeNetRequest.updateDynamicRules({
      removeRuleIds: currentRuleIds, // 先移除所有现有规则
      addRules: rules // 再添加新规则
    });
    console.log('[RogueKiller] 动态规则更新成功', {
      removed: currentRuleIds.length,
      added: rules.length,
      pwsOption: pwsOption
    });
  } catch (error) {
    console.error('[RogueKiller] 更新动态规则失败:', error);
  }
};

// 初始化时更新规则
chrome.storage.local.get(['blocklist'], function(result) {
  if (!result.blocklist) {
    chrome.storage.local.set({blocklist: []});
  }
  blocklist.common.updateDynamicRules();
});

// 黑名单变更时更新规则
chrome.storage.onChanged.addListener(function(changes) {
  if (changes.blocklist) {
    blocklist.common.updateDynamicRules();
  }
});


