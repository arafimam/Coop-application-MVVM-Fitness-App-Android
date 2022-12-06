# Coop-application-MVVM-Fitness-App-Android

# Front End:
  Created with Android Jetpack Compose
  
Login Screen:

![image](https://user-images.githubusercontent.com/86128944/206038023-c18a64f8-9ebc-4730-907a-4210e9b13e3b.png)

Sign Up Screen:

![image](https://user-images.githubusercontent.com/86128944/206038220-47a8a40f-fd51-45bc-bc97-e2bed7f77275.png)

Forgot Password Screen:

![image](https://user-images.githubusercontent.com/86128944/206038331-3bbde1ff-36eb-4567-b424-360155611440.png)

# Implementation Details For managing users:
To know the identity of the user, while the user is logged in and to securely save user credential in the cloud, I used Firebase Authentication.

Documentation for Firebase auth: https://firebase.google.com/docs/auth.


Dashboard Screen:

![image](https://user-images.githubusercontent.com/86128944/206039128-4ce1746f-ee2f-4dc3-8d26-3f9ca2cae965.png)


Insights Screen:

![image](https://user-images.githubusercontent.com/86128944/206039208-4aa0df39-ca00-4f77-be90-20ae346399ad.png)


Profile Screen:

![image](https://user-images.githubusercontent.com/86128944/206039281-d44f7063-d6cc-4040-8f42-512f3170f47a.png)

Exercise Screen:

![image](https://user-images.githubusercontent.com/86128944/206039372-c37eb619-c069-4961-af67-af83aa4c7185.png)

Check Body Type Screen:

![image](https://user-images.githubusercontent.com/86128944/206039460-bdbf99a0-7077-40d9-9d44-9013eeb4e7f4.png)

# App Features & desing Information:

Based on the user's age, hours active and gender a list of exercise for 7 days a week (including rest days) are generate. The user will be reminded with daily exercise using a Local notification channel. Once a user finished all the exercise of a particular day, the user score increases. If the user keeps unfinished workout, then user score will decrease. 

Currently, all user information are stored in Room Database (SQL Database). 

Throughout the making of the application, utilized coroutines and stateflows to handle dynamic data. 


![image](https://user-images.githubusercontent.com/86128944/202968877-6f20b5a5-eb6b-4498-9ba8-f5e361c77e50.png)


