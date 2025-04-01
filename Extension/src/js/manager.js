// 监听状态更新消息
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.type === 'UPDATE_STATUS_BAR') {
    $('#io-desc').text(request.message).addClass('text-success').removeClass('text-danger');
    setTimeout(() => $('#io-desc').text('').removeClass('text-success'), 3000);
  }
  return true;
});

function showSyncAlert(message) {
  const alert = $('#sync-alert');
  $('#sync-alert-message').text(message);
  alert.fadeIn();
  setTimeout(() => alert.fadeOut(), 3000);
}
blocklist.manager = {};
console.log("manager.js执行");

blocklist.manager.handleDeleteBlocklistResponse = function (response) {
  chrome.tabs.query({
    active: true,
    currentWindow: true
  }, function (tabs) {
    chrome.tabs.sendMessage(tabs[0].id, {
      type: 'refresh'
    });
  })
};

blocklist.manager.handleAddBlocklistResponse = function (response) {
  chrome.tabs.query({
    active: true,
    currentWindow: true
  }, function (tabs) {
    chrome.tabs.sendMessage(tabs[0].id, {
      type: 'refresh'
    });
  })
};

//黑名单列表项
blocklist.manager.createBlocklistPattern = function (pattern) {
  let patRow = $(
    `<div class="blocklist-item" style="display:flex;align-items:center;padding:8px 0;margin:0;border-radius:4px;transition:all 0.3s;">
      <div class="delete-btn" style="width:80px;margin-right:20px;text-align:center;"></div>
      <div class="url-text" style="flex:1;font-family:monospace;min-width:0;overflow:hidden;text-overflow:ellipsis;">${pattern}</div>
    </div>`
  );
  
  let deleteBtn = patRow.find('.delete-btn');
  let urlText = patRow.find('.url-text');
  
  // 初始状态
  deleteBtn.html('<span style="color:#dc3545;cursor:pointer;font-weight:500;padding:4px 12px;border-radius:4px;background:#ffebee;user-select:none;">删除</span>');
  urlText.css('text-decoration', 'none');

  deleteBtn.on("click", function() {
    let isDeleted = urlText.css('text-decoration').includes('line-through');
    
    if (!isDeleted) {
      // 删除操作
      chrome.runtime.sendMessage({
        type: blocklist.common.DELETE_FROM_BLOCKLIST,
        pattern: pattern
      }, function() {
        deleteBtn.html('<span style="color:#28a745;cursor:pointer;font-weight:500;padding:4px 12px;border-radius:4px;background:#e8f5e9;user-select:none;">已删除</span>');
        urlText.css('text-decoration', 'line-through');
        urlText.css('color', '#6c757d');
      });
    } else {
      // 恢复操作
      chrome.runtime.sendMessage({
        type: blocklist.common.ADD_TO_BLOCKLIST,
        pattern: pattern
      }, function() {
        deleteBtn.html('<span style="color:#dc3545;cursor:pointer;font-weight:500;padding:4px 8px;border-radius:4px;background:#ffebee;user-select:none;"> 删除 </span>');
        urlText.css('text-decoration', 'none');
        urlText.css('color', 'inherit');
      });
    }
  });
  
  return patRow;
}

blocklist.manager.handleAddBlocklistResponse = function (response) {
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_BLOCKLIST
  },
    blocklist.manager.handleRefreshResponse);
}

blocklist.manager.hideCurrentHost = function (pattern) {
  chrome.runtime.sendMessage({
    'type': blocklist.common.ADD_TO_BLOCKLIST,
    'pattern': pattern
  },
    blocklist.manager.handleAddBlocklistResponse);
  $("#current-blocklink").html(
    '<p style="background:#dff0d8;color:#3c763d;">' +
    chrome.i18n.getMessage('alreadlyBlocked', pattern) +
    '</p>');
}

blocklist.manager.addBlockCurrentHostLink = function (blocklistPatterns) {
  chrome.tabs.query({
    active: true,
    currentWindow: true
  }, function (tabs) {
    let pattern = blocklist.common.getHostNameFromUrl(tabs[0].url);
    let currentBlockLink = $('#current-blocklink');
    
    function updateButton(isBlocked) {
      if (isBlocked) {
        currentBlockLink.html(
          '<button type="button" class="btn btn-danger">取消拉黑 '+ pattern + '</button>'
        );
        currentBlockLink.off('click').click(function() {
          chrome.runtime.sendMessage({
            type: blocklist.common.DELETE_FROM_BLOCKLIST,
            pattern: pattern
          }, function() {
            updateButton(false);
            blocklist.manager.refresh();
          });
        });
      } else {
        currentBlockLink.html(
          '<button type="button" class="btn btn-outline-danger">拉黑 '+ pattern + '</button>'
        );
        currentBlockLink.off('click').click(function() {
          blocklist.manager.hideCurrentHost(pattern);
          updateButton(true);
        });
      }
    }

    updateButton(blocklistPatterns.indexOf(pattern) !== -1);
  });
}

blocklist.manager.handlesearchResponse = function (response) {
  $("#manager-pattern-list").show('fast');

  let length = response.blocklist.length,
    listDiv = $('#manager-pattern-list');
  listDiv.empty();
  searchurl = document.getElementById('searchurl').value;
  if (response.blocklist != undefined && length > 0) {
    for (let i = 0; i < length; i++) {
      /* regex.test(response.blocklist[i]) */
      if(response.blocklist[i].includes(searchurl))
      {
        var patRow = blocklist.manager.createBlocklistPattern(response.blocklist[i]);
        patRow.appendTo(listDiv);
      }
    }
  }
}

blocklist.manager.handlesearchallResponse = function (response) {
  $("#manager-pattern-list").show('fast');
  let length = response.blocklist.length,
    listDiv = $('#manager-pattern-list');
  listDiv.empty();
  if (response.blocklist != undefined && length > 0) {
    for (let i = 0; i < length; i++) {
        var patRow = blocklist.manager.createBlocklistPattern(response.blocklist[i]);
        patRow.appendTo(listDiv);
    }
  }
}




blocklist.manager.handleRefreshResponse = function (response) {
  $("#manager-pattern-list").show('fast');

  let length = response.blocklist.length,
    listDiv = $('#manager-pattern-list');
  listDiv.empty();

  if (response.blocklist != undefined && length > 0) {
    blocklist.manager.addBlockCurrentHostLink(response.blocklist);


    for (let i = 0; i < length; i++) {
      var patRow = blocklist.manager.createBlocklistPattern(response.blocklist[i]);
      patRow.appendTo(listDiv);
    }
  } else {
    blocklist.manager.addBlockCurrentHostLink([]);
  }

}

blocklist.manager.refresh = function () {
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_BLOCKLIST
  },
    blocklist.manager.handleRefreshResponse);
};

blocklist.manager.clickImportButton = function () {

  $("#io-head").text('导入黑名单');
  let submitArea = $("#submit-area");
  submitArea.off('click');
  submitArea.text("save");
  $("#io-desc").text('可以输入以下格式url添加黑名单');
  $("#io-text").val('');
  submitArea.on("click", function () {
    let pattern = $("#io-text").val();
    blocklist.manager.handleImportButton(pattern);
  });
  $("#io-area").toggleClass('io-area-open'); //改变样式，相当于让他显示出来
};

blocklist.manager.clickblacklistButton =function(){

  $("#io-area2").toggleClass('io-area-open2')

}


blocklist.manager.handleImportButton = function (pattern) {
  chrome.runtime.sendMessage({
    type: blocklist.common.ADD_LIST_TO_BLOCKLIST,
    pattern: pattern
  },
    blocklist.manager.handleImportButtonResult);
}

blocklist.manager.handleImportButtonResult = function (response) {
  let showMessage = document.createElement('p');
  showMessage.style.cssText = 'background:#dff0d8;color:#3c763d;padding:10px;';
  showMessage.innerHTML =  '已保存';

  $('#submit-area').after(showMessage);

  setTimeout(function () {
    showMessage.style.visibility = "hidden";
  }, 1000);

  blocklist.manager.refresh();
}
//先发送请求再修改css 以及处理返回
blocklist.manager.clickExportButton = function () {
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_BLOCKLIST
  },
    blocklist.manager.handleExportButton);
};

blocklist.manager.clicksearchButton = function(){
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_BLOCKLIST
  },
  blocklist.manager.handlesearchResponse);
};

blocklist.manager.clicksearchallButton = function(){
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_BLOCKLIST
  },
  blocklist.manager.handlesearchallResponse);
};



blocklist.manager.handleExportButton = function (response) {

  $("#io-head").text('导出模块');

  $('#io-desc').text('你可以选中想要导出的url进行共享');
  let ioText = $("#io-text");
  let blocklist = response.blocklist;

  ioText.val('');
  for (let i = 0, length = blocklist.length; i < length; i++) {
    ioText.val(ioText.val() + blocklist[i] + "\n");
  }

  let submitArea = $("#submit-area");
  submitArea.off('click');
  submitArea.text('复制所有');
  submitArea.click(function () {
    ioText.select();
    document.execCommand('copy');
  });

  $("#io-area").toggleClass('io-area-open');
}

//创建按钮模块
blocklist.manager.createIoButton = function () {

  let export_btn = $("#export");
  export_btn.text(chrome.i18n.getMessage("export"));
  export_btn.on("click", function () {
    blocklist.manager.clickExportButton();
  });

  let import_btn = $("#import");
  import_btn.text(chrome.i18n.getMessage("import"));
  import_btn.on("click", function () {
    blocklist.manager.clickImportButton();
  });

  let blacklist_btn = $("#blacklist");
  blacklist_btn.text("管理黑名单");
  blacklist_btn.on("click", function () {
    blocklist.manager.clickblacklistButton();
  });

  let search_btn = $("#btn-search");
  search_btn.on("click", function () {
    blocklist.manager.clicksearchButton();
  });

  let searchall_btn = $("#btn-searchall");
  searchall_btn.on("click", function () {
    blocklist.manager.clicksearchallButton();
  });


}

blocklist.manager.createBackButton = function () {
  $("#back").text(chrome.i18n.getMessage("back"))
  $("#back").on("click", function () {
    $("#io-area").toggleClass('io-area-open');
  });
}

//黑名单管理返回按钮
blocklist.manager.createBackButton2 = function () {
  $("#back2").text(chrome.i18n.getMessage("back"))
  $("#back2").on("click", function () {
    $("#io-area2").toggleClass('io-area-open2');
  });
}

blocklist.manager.createSettingButton = function () {
  $("#setting").text("一键同步")
  $("#setting").on("click", function () {
    chrome.runtime.sendMessage({
      type: blocklist.common.UPDATE
    }, function() {
      blocklist.manager.refresh();
    });
  });
}


blocklist.manager.createPwsOptionBox = function () {
  chrome.runtime.sendMessage({
    type: blocklist.common.GET_PWS_OPTION_VAL
  },
    blocklist.manager.handlePwsOptionBox);
}

//具体处理逻辑， 如果值为真，按钮的点击状态为true
blocklist.manager.handlePwsOptionBox = function (response) {
  //prop是一个jQuery方法，它用来获取或设置一个元素的属性
  if (response.pws_option == "on")
    $("#pws_option").prop("checked", true);
  //获取一个元素的checked属性，如果它为真，就把val设为"on"，否则就把val设为"off"。这是一种使用三元运算符的简便写法。
  $("#pws_option").on("change", function () {
    let val = $("#pws_option").prop("checked") ? "on" : "off";
    blocklist.manager.clickPwsOptionCheckbox(val);
  });
}

//点击了使用pws功能的按钮（触发函数发送改变val的信号）
blocklist.manager.clickPwsOptionCheckbox = function (val) {
  chrome.runtime.sendMessage({
    type: blocklist.common.CHANGE_PWS_OPTION_VAL,
    val: val
  }, (response) => {
    blocklist.manager.handlePwsOptionCheckboxResult(response);
    // 确保消息处理完成后再更新规则
    chrome.storage.local.set({blocklist_pws_option: val}, () => {
      blocklist.common.updateDynamicRules();
    });
  });
};

//发送改变的信号后，改变的结果要在这里赋给 blocklist.common.pws_option_val
blocklist.manager.handlePwsOptionCheckboxResult = function (response) {
  if (blocklist.common.pws_option_val)
    blocklist.common.pws_option_val = response.pws_option;
}






/* 当HTML文档被完全加载和解析后，不等待样式表、图片和子框架完成加载，就会触发DOMContentLoaded事件 */
document.addEventListener('DOMContentLoaded', function () {
  blocklist.manager.refresh(); //刷新界面，重新显示
  blocklist.manager.createIoButton();//处理导入import 和到处 export 模块
  blocklist.manager.createBackButton();//生成返回按钮
  blocklist.manager.createBackButton2();
  blocklist.manager.createSettingButton();
  blocklist.manager.createPwsOptionBox();//生成管理是否应用pws功能按钮
});

