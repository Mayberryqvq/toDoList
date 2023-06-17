package com.mayberry.todolist.fragment.detail

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.ObjectsCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mayberry.todolist.R
import com.mayberry.todolist.data.model.*
import com.mayberry.todolist.databinding.FragmentDetailBinding
import com.mayberry.todolist.dpToPx
import com.mayberry.todolist.fragment.main.MainViewModel
import java.util.Calendar

class DetailFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    //接受传递的参数
    private val mCurrentToDoArgs: DetailFragmentArgs by navArgs()
    //将args解析出来
    private var mCurrentToDo: ToDo? = null
    private lateinit var binding: FragmentDetailBinding
    private var mPriority = Priority.LOW
    private var mTagData: TagData? = null
    private var lastSelectedTagView: View? = null
    private var mDate = Date(
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrentToDo = mCurrentToDoArgs.currentToDo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化优先级
        initPriorityEvent()
        //初始化日期
        initDateEvent()
        //初始化菜单按钮
        initMenuEvent()
        //设置返回按钮点击事件
        binding.backBtn.setOnClickListener { goBack() }
        //初始化显示数据
        initData()
        //判断是保存还是更新
        if (mCurrentToDo != null) {
            binding.saveBtn.text = "Update"
        } else {
            binding.deleteBtn.visibility = View.INVISIBLE
        }
        //addTag事件
        initAddTagEvent()
        //clearTag事件
        initClearTagEvent()
        //观察tag标签属性
        detailViewModel.tagList.observe(viewLifecycleOwner) {
            //将容器之前的子视图清除
            binding.tagContainer.removeAllViews()
            //改变tag的显示
            it.forEach { tagData ->
                //创建一个TextView显示tag
                TextView(requireContext()).apply {
                    text = tagData.title
                    setBackgroundColor(Color.parseColor(tagData.bgColor))
                    setTextColor(Color.WHITE)
                    setPadding(
                        dpToPx(requireContext(), 15),
                        dpToPx(requireContext(), 10),
                        dpToPx(requireContext(), 15),
                        dpToPx(requireContext(), 10)
                    )
                    setOnClickListener {
                        if (lastSelectedTagView != null) {
                            lastSelectedTagView?.setBackgroundColor(Color.parseColor(mTagData?.bgColor))
                        }
                        mTagData = tagData
                        lastSelectedTagView = this
                        this.setBackgroundColor(requireActivity().getColor(R.color.primary))
                    }
                    binding.tagContainer.addView(this)
                }
            }
        }
    }

    private fun initAddTagEvent() {
        binding.addTagBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_addTagDialogFragment)
        }
    }

    private fun initClearTagEvent() {
        binding.tagContainer.removeAllViews()
    }

    private fun initData() {
        mCurrentToDo?.let {
            //需要将数据显示到界面上
            binding.titleEditText.setText(it.title)
            setPriority(it.priority)
            mDate.year = it.date.year
            mDate.month = it.date.month
            mDate.day = it.date.day
            binding.dateBtn.text = "${it.date.year}-${it.date.month + 1}-${it.date.day}"
            binding.descriptionTextView.setText(it.description)
        }
    }

    private fun setPriority(priority: Priority) {
        mPriority = priority
        binding.priorityHighBtn.setTextColor(requireContext().getColor(R.color.white))
        binding.priorityMiddleBtn.setTextColor(requireContext().getColor(R.color.white))
        binding.priorityLowBtn.setTextColor(requireContext().getColor(R.color.white))
        when (priority) {
            Priority.HIGH -> binding.priorityHighBtn.setTextColor(requireContext().getColor(R.color.primary))
            Priority.MIDDLE -> binding.priorityMiddleBtn.setTextColor(requireContext().getColor(R.color.primary))
            Priority.LOW -> binding.priorityLowBtn.setTextColor(requireContext().getColor(R.color.primary))
        }
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    private fun initMenuEvent() {
        binding.menuBtn.setOnClickListener {
            binding.optionBtnContainer.visibility = View.VISIBLE
            binding.optionBtnContainer.alpha = 1f
            ObjectAnimator.ofFloat(binding.optionBtnContainer, "rotationX", -90f, 0f).apply {
                duration = 300
                interpolator = BounceInterpolator()
                start()
            }
        }
        binding.saveBtn.setOnClickListener {
            if (checkInputValid()) {
                if (mCurrentToDo == null) {
                    //创建ToDo对象
                    val toDoData = ToDo(
                        0,
                        binding.titleEditText.text.toString(),
                        binding.descriptionTextView.text.toString(),
                        mPriority,
                        mDate,
                        Tag(mTagData!!.title, mTagData!!.bgColor)
                    )
                    //插入数据
                    mainViewModel.insertToDoData(toDoData)
                } else {
                    mCurrentToDo?.title = binding.titleEditText.text.toString()
                    mCurrentToDo?.description = binding.descriptionTextView.text.toString()
                    mCurrentToDo?.priority = mPriority
                    mCurrentToDo?.date = mDate
                    if (mTagData != null) {
                        mCurrentToDo?.tag = Tag(mTagData!!.title, mTagData!!.bgColor)
                    }
                    //更新数据
                    mainViewModel.updateToDoData(mCurrentToDo!!)
                }
                //返回上一个页面
                goBack()
            } else {
                Toast.makeText(requireContext(), "ToDo数据不能为空", Toast.LENGTH_SHORT).show()
            }
        }
        binding.deleteBtn.setOnClickListener {
            mainViewModel.deleteToDoData(mCurrentToDo!!)
            goBack()
        }
    }

    private fun checkInputValid(): Boolean {
        return binding.titleEditText.text.isNotEmpty() && binding.descriptionTextView.text.isNotEmpty() && mTagData != null
    }

    private fun optionBtnHideAnim() {
        binding.optionBtnContainer.animate().alpha(0f).rotationX(90f).setDuration(300).setListener(object :Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationCancel(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animator) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initPriorityEvent() {
        //优先级按钮点击事件
        binding.priorityHighBtn.setOnClickListener {
            setPriority(Priority.HIGH)
        }
        binding.priorityMiddleBtn.setOnClickListener {
            setPriority(Priority.MIDDLE)
        }
        binding.priorityLowBtn.setOnClickListener {
            setPriority(Priority.LOW)
        }
    }

    private fun initDateEvent() {
        //日期的点击事件
        binding.dateBtn.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    mDate.year = year
                    mDate.month = month
                    mDate.day = dayOfMonth
                    //将日期显示到textView上
                    binding.dateBtn.text = "$year-${month + 1}-$dayOfMonth"
                },
                mDate.year, mDate.month, mDate.day
            ).show()
        }
    }

}