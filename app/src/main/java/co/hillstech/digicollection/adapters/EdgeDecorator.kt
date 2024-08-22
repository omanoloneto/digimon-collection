package co.hillstech.digicollection.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EdgeDecorator(private val edgePadding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val itemCount = state.itemCount

        when (itemPosition) {
            0 -> outRect.set(view.paddingLeft, edgePadding, view.paddingRight, view.paddingBottom)  // first item
            itemCount - 1 -> outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, edgePadding)  // last item
            else -> outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)  // other items
        }
    }
}
