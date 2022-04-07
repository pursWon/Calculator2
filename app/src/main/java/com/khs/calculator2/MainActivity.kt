package com.khs.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.khs.calculator2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mBinding: ActivityMainBinding? = null

    private val binding get() = mBinding!!

    // 자동으로 완성된 액티비티 메인 바인딩 클래스 인스턴스를 가져왔다.

    companion object {
        const val TAG: String = "로그"
    }
    // 나중에 같이 설정될거라고 lateinit으로 설정
    lateinit var myNumberViewModel: MyNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩과 연결
        setContentView(binding.root)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        /// 뷰 모델 프로바이더를 통해 뷰모델 가져오기
        /// 라이프 사이클을 가지고 있는 녀석을 넣어줄 즉 자기 자신
        /// 내가 가져오고 싶은 뷰모델 클래스를 넣어서 뷰모델을 가져오기

        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)

        // 뷰모델이 가지고 있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다.
        myNumberViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "MainActivity - myNumberViewModel - currentValue 라이브 데이터 값 변경 : $it")
            binding.numberTextview.text = it.toString()
        })
        // 리스너 연결
        binding.plusBtn.setOnClickListener(this)
        binding.minusBtn.setOnClickListener(this)
    }

    // 클릭
    override fun onClick(view: View?) {
       val userInput = binding.userinputEdittext.text.toString().toInt()

       // 뷰모델에 라이브데이터 값을 변경하는 메소드 실행
       when(view){
           binding.plusBtn ->
               myNumberViewModel.updateValue(actionType = ActionType.PLUS, userInput)
           binding.minusBtn ->
               myNumberViewModel.updateValue(actionType = ActionType.MINUS, userInput)
       }
    }
}

