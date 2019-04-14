package com.amqo.randomuser.ui.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.amqo.randomuser.R
import com.amqo.randomuser.ui.list.RandomUserViewHolder

class SwipeToDeleteHandler(
    context: Context, private val onDelete: (RandomUserViewHolder) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background = ColorDrawable(Color.RED)
    private val removeIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp)?.apply {
        setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }
    private val xMarkMargin = context.resources.getDimension(R.dimen.margin_large).toInt()

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.let {
            val listItemViewHolder = viewHolder as RandomUserViewHolder
            onDelete(listItemViewHolder)
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float,
        dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        if (viewHolder.adapterPosition < 0) return

        val view = viewHolder.itemView
        background.apply {
            setBounds(view.right + dX.toInt(), view.top, view.right, view.bottom)
            draw(c)
        }
        // draw the symbol
        removeIcon?.apply {
            val xt = view.top + (view.bottom - view.top - removeIcon.intrinsicHeight) / 2
            setBounds(
                view.right - xMarkMargin - removeIcon.intrinsicWidth, xt,
                view.right - xMarkMargin,
                xt + removeIcon.intrinsicHeight
            )
            draw(c)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}