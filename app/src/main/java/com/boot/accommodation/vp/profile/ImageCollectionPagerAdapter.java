package com.boot.accommodation.vp.profile;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseRecyclerViewAdapter;
import com.boot.accommodation.base.BaseRecyclerViewHolder;
import com.boot.accommodation.util.ImageUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Adapter load hinh anh cho tung tour
 *
 * @author tuanlt
 * @since: 3:28 PM 5/11/2016
 */
public class ImageCollectionPagerAdapter extends BaseRecyclerViewAdapter<String, ImageCollectionPagerAdapter.ViewHolder> {
    private Context context;
    //    private List<String> lstPhoto = new ArrayList<>();//ds hinh anh
    private AdminCollectionAdapter collectionAdapterd;
    AdminCollectionAdapter.ViewHolder viewHolder;

    public ImageCollectionPagerAdapter( Context context, List<String> lstPhoto,AdminCollectionAdapter tripAdapter, AdminCollectionAdapter.ViewHolder viewHolder ) {
        super(lstPhoto);
        this.context = context;
        //        this.lstPhoto = lstPhoto;
        this.collectionAdapterd = tripAdapter;
        this.viewHolder = viewHolder;
    }

    @Override
    public ViewHolder getViewHolder( View view ) {
        return new ViewHolder(view);
    }

    @Override
    public int itemLayout() {
        return R.layout.item_image_trip;
    }

    @Override
    protected void bindViewHoder( ViewHolder holder, int position ) {
        holder.bindData(items.get(position), position);
    }


    //    @Override
    //    public Object instantiateItem( ViewGroup collection, int position ) {
    //        LayoutInflater inflater = LayoutInflater.from(context);
    //        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_image_trip, collection, false);
    //        ButterKnife.bind(this, layout);
    //        ImageUtil.loadImage(ivPhoto,lstPhoto.get(position));
    //        layout.setTag(position);
    //        layout.setOnClickListener(this);
    //        collection.addView(layout);
    //        return layout;
    //    }
    //
    //    @Override
    //    public int getCount() {
    //        return  this.lstPhoto.size();
    //    }
    //
    //    @Override
    //    public boolean isViewFromObject( View view, Object object ) {
    //        return view == object;
    //    }
    //
    //    @Override
    //    public void destroyItem(final ViewGroup container, final int position, final Object object) {
    //        ((ViewPager) container).removeView((ViewGroup) object);
    //    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.llPhoto)
        LinearLayout llPhoto;
        ViewHolder( View view ) {
            super(view);
        }

        public void bindData( String data, int position ) {
            this.data = data;
            this.position = position;
            ImageUtil.loadImage(ivPhoto,ImageUtil.getImageUrl(items.get(position)));
            if(items.size() == 1){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)ivPhoto.getLayoutParams();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                ivPhoto.setLayoutParams(params);
            }
        }

        @OnClick({R.id.ivPhoto})
        public void OnClick( View v ) {
            collectionAdapterd.clickImage(viewHolder,position);
        }
    }
}
