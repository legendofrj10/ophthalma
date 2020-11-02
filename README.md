# ophthalma
Retina Anomaly Detection Application(RADA)
##Description
RADA is a Hospital dedicated software, prepared to work as an user interface for health workers and connect to other machines and software. This is a desktop based application that allows registered users to store appointments/register new patients/scan their body organs/ do analysis on numbers in desktop. The advantage with this is Health Workers can access their accounts with proper access control. The app is built in such a way that there are four types of accounts according to the Hierarchy .
###Prerequisites
####Dependencies
    software : Mysql client
    Installation for windows: 
    https://dev.mysql.com/downloads/installer/
    Click on first download button and install in your system.
    ####Database : eye_scanner
    
    Tables :
    1.users(username(varchar20), password(varchar20), email(varchar50), 				num_id(int), user_id(varchar10), account_type(enum{LabTechnician, 			Doctor, Admin, Optometrist}), picture(longblob), org_id(varchar20), 				enabled(boolean), patients(int), userss(int) )

    2.patients(name(varchar20), age(int), dob(Date), gender(enum{male, female}), l		Last_diagnosed(Date), last_visit(Date), numeric_id(int), 							patient_id(varchar15), notified(boolean), visits(int), reg_day(Date))
    
    3.organisation(name(varchar30), id(varchar20), licence_no(varchar20), 				subscription(enum{platinum, gold, silver}), picture(longblob), 					purchase_day(Date), enabled(boolean)




