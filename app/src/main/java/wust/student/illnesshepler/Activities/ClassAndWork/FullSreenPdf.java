package wust.student.illnesshepler.Activities.ClassAndWork;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import wust.student.illnesshepler.Activities.Fragments.CurseFragment;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.AnimationUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class FullSreenPdf extends AppCompatActivity implements View.OnClickListener {
    boolean teamp = true;
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private static final String FILENAME = "sample.pdf";
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;
    private PhotoView mImageView;
    private ImageView mButtonPrevious;
    private ImageView mButtonNext;
    private ImageView mZoom;
    private IndicatorSeekBar mseekBar;
    private LinearLayout bottom;
    private int mPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sreen_pdf);

        Bundle bundle = getIntent().getExtras();
        mPageIndex = bundle.getInt("page");
       initlayout();

        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        try {
            openRenderer(this);
            showPage(mPageIndex);
            setseeckbar();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setseeckbar()
    {
        mseekBar.setMax(getPageCount());
        mseekBar.setMin(1);
        mseekBar.setProgress(mCurrentPage.getIndex());
        mseekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                showPage(seekBar.getProgress());
            }
        });
    }
    public void initlayout()
    {
        mImageView = (PhotoView) findViewById(R.id.full_photoView);
        mImageView.setZoomable(true);
        mButtonPrevious = (ImageView) findViewById(R.id.previous);
        mButtonNext = (ImageView) findViewById(R.id.next);
        mZoom = (ImageView) findViewById(R.id.zoom);
        mseekBar = (IndicatorSeekBar) findViewById(R.id.seekBar);
        bottom=(LinearLayout)findViewById(R.id.bottom_linear) ;
        // Bind events.
        mButtonPrevious.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mZoom.setOnClickListener(this);
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        if (null != mCurrentPage) {
//            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
//        }
//        super.onSaveInstanceState(outState);
//    }

    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(MainActivity.PdfDDruaction, FILENAME);
        if (!file.exists()) {
            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }
    }

    private void closeRenderer() throws IOException {
        if (null != mCurrentPage) {
            Log.d("test", "mCurrentPage==null");
            mCurrentPage.close();
        }
        Log.d("test", "mCurrentPage!=null");
        mPdfRenderer.close();
        mFileDescriptor.close();
        mImageView.setColorFilter(getResources().getColor(R.color.colorAccent));
    }

    private void showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        mCurrentPage = mPdfRenderer.openPage(index);
        mseekBar.setProgress(mCurrentPage.getIndex());
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth() * 3, mCurrentPage.getHeight() * 3,
                Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mImageView.setImageBitmap(bitmap);
        updateUi();
    }

    private void updateUi() {
        int index = mCurrentPage.getIndex();
        int pageCount = mPdfRenderer.getPageCount();
        mButtonPrevious.setEnabled(0 != index);
        mButtonNext.setEnabled(index + 1 < pageCount);
//        getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
    }

    public int getPageCount() {
        return mPdfRenderer.getPageCount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous: {
                // Move to the previous page
                showPage(mCurrentPage.getIndex() - 1);
                break;
            }
            case R.id.next: {
//                // Move to the next page
                showPage(mCurrentPage.getIndex() + 1);
                break;
            }
            case R.id.zoom: {
//                // TODO
                Intent intent = new Intent(FullSreenPdf.this, CurseAndWork.class);
                Bundle bundle = new Bundle();
                bundle.putInt("page", mCurrentPage.getIndex());
                intent.putExtras(bundle);
                setResult(100,intent);
                finish();
//                startActivity(intent);
//                CurseFragment.newInstance(mCurrentPage.getIndex());
                break;
            }
            case R.id.full_photoView:
//                mButtonNext.setVisibility(View.VISIBLE);
//                mButtonPrevious.setVisibility(View.VISIBLE);
                // 向左边移入

                if (teamp) {
                    mButtonPrevious.setVisibility(View.GONE);
                    mButtonNext.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
//                    mseekBar.setVisibility(View.GONE);
                    bottom.setAnimation(AnimationUtil.outToBottomAnimation(this));
//                    mseekBar.setAnimation(AnimationUtil.outToBottomAnimation(this));
                    mButtonPrevious.setAnimation(AnimationUtil.outToLeftAnimation());
                    mButtonNext.setAnimation(AnimationUtil.outToRightAnimation());
                    teamp = false;
                } else {
                    mButtonNext.setVisibility(View.VISIBLE);
                    mButtonPrevious.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                    mButtonNext.setAnimation(AnimationUtil.inFromRightAnimation());
                    mButtonPrevious.setAnimation(AnimationUtil.inFromLeftAnimation());
                    bottom.setAnimation(AnimationUtil.inFromBottomAnimation(this));
//                    mseekBar.setAnimation(AnimationUtil.inFromBottomAnimation(this));
                    teamp = true;
                }
        }
    }
}
