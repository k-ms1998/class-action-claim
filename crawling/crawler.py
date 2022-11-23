from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time
import pyperclip

driver = webdriver.Chrome()
options = webdriver.ChromeOptions()
options.add_experimental_option('excludeSwitches',['enable-logging'])
driver = webdriver.Chrome(options=options)
ID = ''
PassWd = ''

url_login = "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp?rtUrl=sjpt.sejong.ac.kr/main/view/Login/doSsoLogin.do?p="
url_myPage = "https://sjpt.sejong.ac.kr/main/view/Login/doSsoLogin.do?p="

driver.get(url_login)
time.sleep(1)

#id, pw 입력할 곳 찾기
tag_id = driver.find_element(By.NAME,'id')
tag_pw = driver.find_element(By.NAME,'password')
tag_id.clear()
tag_pw.clear()
time.sleep(1)

tag_id.click()
pyperclip.copy(ID)
tag_id.send_keys(Keys.CONTROL, 'v')
time.sleep(1)

tag_pw.click()
pyperclip.copy(PassWd)
tag_pw.send_keys(Keys.CONTROL, 'v')
time.sleep(1)

WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'loginBtn'))).click()
# login_btn = driver.find_element(By.ID,'loginBtn')
# login_btn.click()
# time.sleep(3)

# name = driver.find_element(By.CLASS_NAME,'txt_b').text

# print("학생정보: "+name)

WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_wfrLeftTreeMenu_treLeftMenu_node_22'))).click()

WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_wfrLeftTreeMenu_treLeftMenu_node_23'))).click()

WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_wfrLeftTreeMenu_treLeftMenu_node_25'))).click()

time.sleep(1)
# 드롭 다운 메뉴에서 일일이 확인

for i in range(1,84):
    WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_cbb_deptCd_button'))).click()
    # driver.find_element(By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_cbb_deptCd_button').click()
    time.sleep(1)
    xpath = 'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_cbb_deptCd_itemTable_{}'.format(i)
    
    WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.XPATH,"//*[@id='"+xpath+"']"))).click()

    # option = driver.find_element(By.XPATH,"//*[@id='"+xpath+"']")

    # # driver.execute_script("arguments[0].scrollIntoView();", option)
    # option.click()
    time.sleep(2)
    WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_btn_saerch'))).click()

    # driver.find_element(By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_wq_uuid_343').click()
    time.sleep(1)

    WebDriverWait(driver,10).until(EC.element_to_be_clickable((By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_btn_excel'))).click()

    # driver.find_element(By.ID,'mf_tabMainCon_contents_SELF_STUDSELF_SUB_30SELF_MENU_10SueOpenTimeQ_body_btn_excel').click()
    time.sleep(1)

time.sleep(5)





