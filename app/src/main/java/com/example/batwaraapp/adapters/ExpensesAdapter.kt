package com.example.batwaraapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batwaraapp.databinding.ItemExpenseBinding
import com.example.batwaraapp.datamodels.Expense
import com.example.batwaraapp.utils.InterfaceUtils.uniClick

class ExpensesAdapter : RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    private var expenseList = ArrayList<Expense>()

    private var onExpenseClickListener: ((Expense) -> Unit)? = null

    private var onExpenseRemoveListener: ((Expense) -> Unit)? = null

    fun setDataSet(expenseList: ArrayList<Expense>?) {
        expenseList?.let { this.expenseList = expenseList }
        notifyDataSetChanged()
    }

    fun setExpenseRemoveListener(onExpenseRemoveListener: ((Expense) -> Unit)?) {
        this.onExpenseRemoveListener = onExpenseRemoveListener
    }

    inner class ExpensesViewHolder(var binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.expense = expense
            binding.removeExpense.uniClick (true) {
                onExpenseRemoveListener?.invoke(expense)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        return ExpensesViewHolder(ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.bind(expenseList[position])
    }

    override fun getItemCount(): Int  = expenseList.size

}