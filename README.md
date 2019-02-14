Steps to integrate SMSRetrievalApi in android application: 

build.gradle (app level) : 
***************************
add this dependencies :

//for google auth and if you want to use hint number request
implementation 'com.google.android.gms:play-services-auth:16.0.1'

//for SmsRetriever 
implementation 'com.google.android.gms:play-services-auth-api-phone:16.0.0'


manifest.xml
***************************
add a broadcast receiver with following intent filter :

<intent-filter>
      <action android:name="com.google.android.gms.auth.api.phone.SMS_RETRIEVED" />
</intent-filter>


for example :
-------------
        <receiver
                android:name=".receiver.MySMSBroadcastReceiver"
                android:exported="true">
            <intent-filter>
                <action android:name="com.google.android.gms.auth.api.phone.SMS_RETRIEVED" />
            </intent-filter>
        </receiver>


application module :
********************
> add code to get hash key from application
> create a broadcastReceiver to receiver otp code
> register broadcastReceiver with intent filter 
ex.
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        applicationContext.registerReceiver(mySMSBroadcastReceiver, intentFilter)

> start SmsRetrieverListener by calling below method :
ex.
private fun startSMSListener() {
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
 
            Toast.makeText(this, "SMS Retriever starts", Toast.LENGTH_LONG).show()
        }

        task.addOnFailureListener {
           
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

> register data callback from broadcastReceiver to get otp where you want..

***************************AND*********************************

if you want to use hintRequestIntent to get user number from phone then :

> setup googleApiClient 
ex.
	mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, this)
            .addApi(Auth.CREDENTIALS_API)
            .build()

> request for numberHint by calling this method :
ex.
    private fun getHintPhoneNumber(){
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent = Auth.CredentialsApi.getHintPickerIntent(
            mGoogleApiClient, hintRequest
        )
        startIntentSenderForResult(intent.intentSender, RESOLVE_HINT, null, 0, 0, 0)
    }

> get selected number in onActivityResult like :

     if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {

                var credential: Credential = data!!.getParcelableExtra(Credential.EXTRA_KEY)
                // credential.getId();  <-- will need to process phone number string
            }
        }


:::::: process with number now

working ... :-)

