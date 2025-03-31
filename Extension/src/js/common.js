let blocklist = {};
blocklist.common = {};
blocklist.common.pws_option_val = "off";
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
              sendResponse({
                pattern: request.pattern
              });
            });
          } else {
            sendResponse({
              pattern: request.pattern
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

//监听是否浏览黑名单
chrome.webRequest.onBeforeRequest.addListener(
  function(details) {
    console.log('[RogueKiller] 开始处理请求:', details.url);
    chrome.storage.local.get(['blocklist'], function(result) {
      if (chrome.runtime.lastError) {
        console.error('[RogueKiller] 获取黑名单失败:', chrome.runtime.lastError);
        return;
      }
      console.log('[RogueKiller] 当前黑名单:', result.blocklist);
      console.debug('[RogueKiller] 检查URL:', details.url, '对比黑名单:', result.blocklist);
      
      // 检查是否在黑名单中
      if (result.blocklist && result.blocklist.some(pattern => {
        try {
          const urlObj = new URL(details.url);
          const host = urlObj.hostname;
          return host.includes(pattern) || details.url.includes(pattern);
        } catch (e) {
          return details.url.includes(pattern);
        }
      })) {
        // 显示醒目通知并阻止页面加载
        chrome.notifications.create({
          type: 'basic',
          title: '⚠️ 安全警告 ⚠️',
          message: `检测到危险网站!\n网址: ${details.url}\n\n建议立即关闭此页面`,
          iconUrl: '/images/icon.png',
          priority: 2,
          requireInteraction: true,
          buttons: [{title: '了解风险'}]
        }, function(notificationId) {
          if (chrome.runtime.lastError) {
            console.error('通知创建失败:', chrome.runtime.lastError);
          }
        });
        return {cancel: true};
      }
      var url = blocklist.common.getHostNameFromUrl(details.url);
      var arr = result.blocklist || [];
      if (arr.includes(url)) {
        var notifyOptions = {
          type: 'basic',
          title: '安全警告',
          iconUrl: '/images/icon.png',
          message: '您正在访问危险网站: '+url,
          priority: 2,
          requireInteraction: true
        }
        console.log('[RogueKiller] 检测到危险网站:', url);
        console.debug('[RogueKiller] 通知参数:', notifyOptions);
        chrome.notifications.create('notify_'+Date.now(), notifyOptions, function(notificationId) {
          if (chrome.runtime.lastError) {
            console.error('[RogueKiller] 通知创建失败 - 错误详情:', {
              error: chrome.runtime.lastError,
              url: url,
              time: new Date().toISOString()
            });
          } else {
            console.log('[RogueKiller] 通知创建成功 - 详情:', {
              id: notificationId,
              url: url,
              time: new Date().toISOString()
            });
          }
        });
      }
    });
    return {cancel: false};
  },
  {urls: ["<all_urls>"]},
  ["blocking"]
);


