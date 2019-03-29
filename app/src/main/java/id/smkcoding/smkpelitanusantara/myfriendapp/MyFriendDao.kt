package id.smkcoding.smkpelitanusantara.myfriendapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyFriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriend)

    @Query("SELECT * FROM MyFriend")
    fun ambilSemuaTeman(): LiveData<List<MyFriend>>

    @Query("DELETE FROM MyFriend WHERE temanId = :temanId")
    fun hapusTeman (temanId: Long?)

    @Query("UPDATE MyFriend SET nama =:nama, kelamin=:kelamin,email=:email,telp=:telp,alamat=:alamat WHERE temanId =:temanId")
    fun updateTeman(temanId: Long, nama:String?,kelamin : String?, email : String?,telp: String?,alamat: String?)
}