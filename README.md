#Wudu Android Social
###NOTE: Be aware that this is not almost finished


Wudu-Android-Social is an Android with goal to simplify third party logins. Currently the following social networks are supported:

*	Facebook
*	Twitter
*	Google+

In contrary to [socialauth-android](https://code.google.com/p/socialauth-android/) it is using where possible native apis. It is heavily inspired by [SimpleAuth](https://github.com/calebd/SimpleAuth) for iOS.

##General Usage

Fist, initalize and register the Providers (most of the time in onCreate) like that:

        googleAuthenticationProvider = new GoogleAuthenticationProvider(activity,callbacks);
		SocialAuthManagerFactory.getInstance(context).
				registerProvider(googleAuthenticationProvider);

Each Provider requires more ore less parameters, all passed in the constructor of it.
Unfortunately the Social Authentication Libs are deeply integrated in the lifecylce of the Activity, which makes abstraction not trivial. You have to let each Event the SocialAuthManger know.

	        SocialAuthManagerFactory.getInstance(this).
	        onCreate(getIntent());
	        
At the Moment the following events are required to be called on the manager:

* onCreate
* onNewIntent
* onAcvitiyResult
* onStart
* onStop	   


Now you should be able to call:


	youProvider.login()
	
	
The callbacks should be called as expected	


See the sample for detail.
     
##Network specific usage

### Facebook

######[Long Story:](https://developers.facebook.com/docs/android/login-with-facebook/v2.0) (you only need to do the setup steps)

######Short(er) Story

Create an App at the Developer Dashboard and fill in your information such as Hash,Packagename und Activity (with full Package name) you want to us.

Note: to find auth the Hash, you can call this method:

	FacebookUtils.getHashForApiConsole(..)
which returns a list of hashes, the first one should be yours


Add your app id from the dashboard to your strings.xml

	<string name="facebook_app_id">YOU_APP_ID</string>



Add this the your manifest

	<uses-permission android:name="android.permission.INTERNET"/>
	
	
	<application>
	
	... 
	<activity
    	android:name="com.facebook.LoginActivity"
        android:label="LoginAcitivty"/>
        
        
	<meta-data
		android:name="com.facebook.sdk.ApplicationId"
    	android:value="@string/facebook_app_id"/>        

	
	</application>

### Twitter


Sign up at [Twitter Developer](https://dev.twitter.com/)

Create an [App](https://apps.twitter.com/).

Do this modication to your activity manifest

	   <activity
        android:name=
        "at.peterzainzinger.wudu.android.social.sample.MainActivity"
                android:label="@string/app_name"
                android:launchMode="singleTask"

                >

            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>

            <intent-filter>
                <action android:name="android.intent.action.VIEW"/>

                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="android.intent.category.BROWSABLE"/>

                <data
                        android:host="sample"
                        android:scheme="oauth"/>
            </intent-filter>

        </activity>


NOTE: android:launchMode="singleTask" is very important, because otherwise it want be called

Configure the Twitter Provider with

*	ConsumerKey
*	ConsumerSecret
*	twitterCallbackUrl (in this sample oauth://sample @see manifest)

###Google+

[Setup](https://developers.google.com/+/mobile/android/getting-started) Setup 1 - Setup 3



##Next Steps

* Complete Error Handling (especially Google+ is not complete yet)
* Add custom abstract Activity to require less boilerplate
*	Code Cleanup
*	Profile Loading 
*	Upload to a Maven Repo
*	Add new Providers 



##License

####The MIT License
===============

Copyright (c) 2009 Anton Grigoryev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.








	


	




        
 
