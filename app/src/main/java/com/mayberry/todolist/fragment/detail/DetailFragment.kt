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
    private val mCurrentToDoArgs: DetailFragmentArgs by navArgs()
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
        initPriorityEvent()
        initDateEvent()
        initMenuEvent()
        binding.backBtn.setOnClickListener { goBack() }
        initData()
        if (mCurrentToDo != null) {
            binding.saveBtn.text = "Update"
        } else {
            binding.deleteBtn.visibility = View.INVISIBLE
        }
        initAddTagEvent()
        detailViewModel.tagList.observe(viewLifecycleOwner) {
            binding.tagContainer.removeAllViews()
            it.forEach { tagData ->
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

    private fun initData() {
        mCurrentToDo?.let {
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
                    val toDoData = ToDo(
                        0,
                        binding.titleEditText.text.toString(),
                        binding.descriptionTextView.text.toString(),
                        mPriority,
                        mDate,
                        Tag(mTagData!!.title, mTagData!!.bgColor)
                    )
                    mainViewModel.insertToDoData(toDoData)
                } else {
                    mCurrentToDo?.title = binding.titleEditText.text.toString()
                    mCurrentToDo?.description = binding.descriptionTextView.text.toString()
                    mCurrentToDo?.priority = mPriority
                    mCurrentToDo?.date = mDate
                    if (mTagData != null) {
                        mCurrentToDo?.tag = Tag(mTagData!!.title, mTagData!!.bgColor)
                    }
                    mainViewModel.updateToDoData(mCurrentToDo!!)
                }
                goBack()
            } else {
                Toast.makeText(requireContext(), "ToDo数据不能为空", Toast.LENGTH_LONG).show()
            }
        }
        binding.deleteBtn.setOnClickListener {
            mainViewModel.deleteToDoData(mCurrentToDo!!)
            goBack()
        }
    }

    private fun checkInputValid(): Boolean {
        return binding.titleEditText.text.isNotEmpty() && mTagData != null
    }

    private fun optionBtnHideAnim() {
        binding.optionBtnContainer.animate().alpha(0f).rotationX(90f).setDuration(300).setListener(object :Animator.AnimatorListener{

            override fun onAnimationStart(animation: Animator?) { }

            override fun onAnimationEnd(animation: Animator?) {
                binding.optionBtnContainer.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) { }

            override fun onAnimationRepeat(animation: Animator?) { }

        })
    }

    private fun initPriorityEvent() {
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
        binding.dateBtn.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                object:DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        mDate.year = year
                        mDate.month = month
                        mDate.day = dayOfMonth
                        binding.dateBtn.text = "$year-${month + 1}-$dayOfMonth"
                    }
                },
                mDate.year, mDate.month, mDate.day
            ).show()
        }
    }

}