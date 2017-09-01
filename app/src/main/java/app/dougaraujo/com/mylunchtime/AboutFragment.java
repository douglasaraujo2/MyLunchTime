package app.dougaraujo.com.mylunchtime;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {
    private TextView tvVersionCode;
    private Button shareButton;
    private ShareDialog shareDialog;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_about, container, false);
        tvVersionCode = (TextView) itemView.findViewById(R.id.tvVersionCode);
        shareButton = (Button) itemView.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);

        shareDialog = new ShareDialog(this);
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            tvVersionCode.setText(getString(R.string.txt_version) + " " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        // Inflate the layout for this fragment
        return itemView;
    }

    public void shareContent(View view) {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://duxinnovation.com"))
                .build();
        shareDialog.show(linkContent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shareButton) {
            shareContent(v);
        }
    }
}
