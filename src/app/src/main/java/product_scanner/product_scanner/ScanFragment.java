package product_scanner.product_scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Nguyen Khang on 11/1/2017.
 */

public class ScanFragment extends Fragment {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private ShareButton shareButton;

    private static final int SCAN_BARCODE = 123 ;
    private TextView txt_scannumber;
    private Button btn_clickscan;
    private Context mContext;

    //
    ViewPager viewPager;
    ScreenShootAdapter screenShootAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        mContext= getActivity();
        findView(v);
        setOnClick();

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.nike_sample1);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Messi bucu")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareButton.setShareContent(content);

        //
        screenShootAdapter = new ScreenShootAdapter(getContext());
        viewPager.setAdapter(screenShootAdapter);

        return v;

    }
    private void setOnClick() {
        btn_clickscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),ScanBarcode.class);
                startActivityForResult(i,SCAN_BARCODE);
            }
        });
    }

    private void findView(View v) {

        txt_scannumber= v.findViewById(R.id.code_info);
        btn_clickscan=v.findViewById(R.id.btn_clickscan);
        shareButton=v.findViewById(R.id.fb_share_button);



        btn_clickscan=v.findViewById(R.id.button_scan);
        //
        viewPager = v.findViewById(R.id.screenshoot_slider);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == SCAN_BARCODE){
                if(resultCode== Activity.RESULT_OK){
                    txt_scannumber.setText(data.getStringExtra("barcode"));
                    super.onActivityResult(requestCode, resultCode, data);
                    callbackManager.onActivityResult(requestCode, resultCode, data);

                }
            }
        }

}
