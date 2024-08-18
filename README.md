 val viewmodel =MainViewModel(UserRepoImp(LocalDataSourceImp(null,SharedPreferenceImp.getInstance(applicationContext)))) 
 --->>>>this is if u want the sharedPreference only 
 ----------------------------------------------------------------------------------------------------------------------------------
 val viewmodel =MainViewModel(UserRepoImp(LocalDataSourceImp(RoomDataBaseImp.getInstance(applicationContext),null))) 
 ----->>>and this if u want the roomdata base only 
 ----------------------------------------------------------------------------------------------------------------------------------
  val viewmodel =MainViewModel(UserRepoImp(LocalDataSourceImp(RoomDataBaseImp.getInstance(applicationContext),SharedPreferenceImp.getInstance(applicationContext))))
 ---->>and this if u will use both sharedPreference and roomdata

 This is  an small example of how will u take an object from viewmodel but doint forget to use Factory 
