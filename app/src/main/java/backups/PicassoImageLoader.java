package backups;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yyydjk.library.BannerLayout;

public class PicassoImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Picasso.with(context).load(path).into(imageView);
    }
}
