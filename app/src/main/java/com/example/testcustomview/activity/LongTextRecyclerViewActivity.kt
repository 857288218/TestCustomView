package com.example.testcustomview.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testcustomview.R
import com.example.testcustomview.Sentence
import com.example.testcustomview.SentenceTextView
import com.example.testcustomview.databinding.ActivityLongTextRecyclerViewBinding
import com.example.testcustomview.databinding.ItemImageBinding
import com.example.testcustomview.databinding.ItemSentenceBinding
import com.example.testcustomview.databinding.ItemTextBinding
import java.util.*

class LongTextRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLongTextRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leakActivity = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_long_text_recycler_view)
        intent.getIntExtra("a", 0)
        binding.recyclerView.adapter = Adapter()
        Handler(Looper.getMainLooper()).postDelayed({
//            recyclerView?.scrollBy(0, 500)
//            (binding.recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, -300)
        }, 1000)
    }

    companion object {
        var leakActivity: Activity? = null
    }
    class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var curItemPos = -1
        private var curHighlightSentence: Sentence? = null
        private var sentencesList = ArrayList<ArrayList<Sentence>>().apply {
            add(ArrayList<Sentence>().apply {
                add(Sentence(0, 2000, "我是大好人！难道不是吗？哈哈~", "1"))
                add(Sentence(2000, 4000, "不管你们怎么说，反正我觉得我是那么我就是，不接受任何反驳！", "1"))
            })

            add(ArrayList<Sentence>().apply {
                add(Sentence(4000, 6000, "我是大好人！难道不是吗？哈哈~", "2"))
                add(Sentence(6000, 8000, "不管你们怎么说，反正我觉得我是那么我就是，不接受任何反驳！", "2"))
                add(Sentence(8000, 10000, "就这样吧！", "2"))
            })

            add(ArrayList<Sentence>().apply {
                add(Sentence(10000, 12000, "我是大好人！难道不是吗？哈哈~", "3"))
            })

            add(ArrayList<Sentence>().apply {
                add(Sentence(12000, 14000, "我是大好人！难道不是吗？哈哈~", "4"))
                add(Sentence(14000, 16000, "不管你们怎么说，反正我觉得我是那么我就是，不接受任何反驳！", "4"))
                add(Sentence(16000, 18000, "不管你们怎么说，反正我觉得我是那么我就是，不接受任何反驳！", "4"))
                add(Sentence(18000, 20000, "就这样吧！", "4"))
                add(Sentence(20000, 21000, "就这样吧！~", "4"))
            })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                0 -> TextViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_text, parent, false))
                1 -> ImageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false))
                else -> SentenceViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_sentence, parent, false))
            }
        }

        override fun getItemCount(): Int = 2 + sentencesList.size

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> 0
                1 -> 1
                else -> 2
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is SentenceViewHolder) {
                holder.binding.sentenceTv.setSentenceList(sentencesList[position - 2])
                if (curItemPos != position) {
                    holder.binding.sentenceTv.removeHighlightSentence()
                } else {
                    holder.binding.sentenceTv.setHighlightSentence(curHighlightSentence)
                }

                holder.binding.sentenceTv.setOnSentenceClickListener(object : SentenceTextView.OnSentenceClickListener {
                    override fun onClick(sentence: Sentence?) {
                        for ((i, sentences) in sentencesList.withIndex()) {
                            for ((j, s) in sentences.withIndex()) {
                                if (s == sentence) {
                                    curHighlightSentence = s
                                    if (curItemPos - 2 != i) {
                                        val needRefreshPos = curItemPos
                                        curItemPos = i + 2                  // 有两个header
                                        if (needRefreshPos != -1) {
                                            notifyItemChanged(needRefreshPos)
                                        }
                                    }
                                    break
                                }
                            }
                        }
                    }
                })
            } else if (holder is ImageViewHolder) {
                holder.itemView.setOnClickListener {
                    val intent = Intent(it.context, BindingAdapterPositionActivity::class.java)
                    intent.putExtra("large_data", ByteArray(512 * 1024))
                    it.context.startActivity(intent)
                }
            } else if (holder is TextViewHolder) {
                holder.itemView.setOnClickListener {
                    it.context.startActivity(Intent(it.context, ViewStubActivity::class.java))
                }
            }
        }

        class TextViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)
        class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
        class SentenceViewHolder(val binding: ItemSentenceBinding) : RecyclerView.ViewHolder(binding.root)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        finish()
        startActivity(Intent(this, LongTextRecyclerViewActivity::class.java))
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}