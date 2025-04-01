console.log("my_alert.js开始执行");

// 提取当前网址的域名
function getDomain(url) {
  try {
    const domain = new URL(url).hostname;
    return domain.startsWith('www.') ? domain.substring(4) : domain;
  } catch {
    return '';
  }
}

// 创建警示框
function showAlert(domain) {
  const alertBox = document.createElement('div');
  alertBox.style.position = 'fixed';
  alertBox.style.top = '50%';
  alertBox.style.left = '50%';
  alertBox.style.transform = 'translate(-50%, -50%)';
  alertBox.style.width = '400px';
  alertBox.style.padding = '25px';
  alertBox.style.backgroundColor = 'rgba(255, 68, 68, 0.9)';
  alertBox.style.color = 'white';
  alertBox.style.zIndex = '999999';
  alertBox.style.textAlign = 'center';
  alertBox.style.borderRadius = '8px';
  alertBox.style.boxShadow = '0 4px 20px rgba(0,0,0,0.2)';
  
  alertBox.innerHTML = `
    <div style="display:flex;align-items:center;margin-bottom:15px;">
      <h3 style="margin:0;font-size:18px;">Rogue Killer 安全警告</h3>
    </div>
    <p style="margin-bottom:20px;font-size:14px;line-height:1.5;">
      您正在访问已知的恶意网站:<br>
      <strong>${domain}</strong>
    </p>
    <div style="text-align:center;">
      <button id="alert-close" style="padding:8px 24px;background:#fff;color:#ff4444;border:none;border-radius:20px;cursor:pointer;font-weight:bold;font-size:14px;">
        我知道了
      </button>
    </div>
    <div style="margin-top:15px;font-size:12px;color:rgba(255,255,255,0.7);">
      Rogue Killer 安全防护系统
    </div>
  `;

  document.body.appendChild(alertBox);
  
  document.getElementById('alert-close').addEventListener('click', () => {
    alertBox.remove();
  });
}

// 检查当前网址是否在blocklist中
chrome.storage.local.get(['blocklist'], (result) => {
  const blocklist = result.blocklist || [];
  const currentDomain = getDomain(window.location.href);
  
  if (blocklist.some(domain => currentDomain.includes(domain))) {
    showAlert(currentDomain);
  }
});
