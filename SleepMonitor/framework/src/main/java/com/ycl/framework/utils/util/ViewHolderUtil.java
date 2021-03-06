package com.ycl.framework.utils.util;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ycl.framework.module.CustomImageSizeModelFutureStudio;

public class ViewHolderUtil {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static void loadSizeUrlCrop(String url, ImageView iv) {
        GlideProxy.loadImgWithModel(iv, new CustomImageSizeModelFutureStudio(url, 0));
    }

    public static void loadSizeUrlFit(String url, ImageView iv) {
        GlideProxy.loadImgWithModel(iv,new CustomImageSizeModelFutureStudio(url, 1));
    }

    public static CustomImageSizeModelFutureStudio getCustomModel(String url, int type) {
        CustomImageSizeModelFutureStudio customModel = new CustomImageSizeModelFutureStudio();
        customModel.setBaseImageUrl(url);
        customModel.setTypeUrl(type);
        return customModel;
    }
}
