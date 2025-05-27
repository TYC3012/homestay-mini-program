from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

# 1. 初始化浏览器驱动
service = Service(ChromeDriverManager().install())
browser = webdriver.Chrome(service=service)

# 2. 访问网页
browser.get("https://www.baidu.com")

# 3. 查找元素（现在browser已定义）
try:
    element = browser.find_element(By.CLASS_NAME, 's_ipt')
    print("成功找到元素:", element)
except Exception as e:
    print("查找元素失败:", e)

# 4. 关闭浏览器
browser.quit()