package wust.student.illnesshepler.Activities.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.AnimationUtil;


public class CurseFragment extends Fragment implements View.OnClickListener {
    boolean teamp = true;
    /**
     * Key string for saving the state of current page index.
     */
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";

    /**
     * The filename of the PDF.
     */
    private static final String FILENAME = "sample.pdf";
    Bitmap bitmap2;
    /**
     * File descriptor of the PDF.
     */
    private ParcelFileDescriptor mFileDescriptor;

    /**
     * {@link PdfRenderer} to render the PDF.
     */
    private PdfRenderer mPdfRenderer;

    /**
     * Page that is currently shown on the screen.
     */
    private PdfRenderer.Page mCurrentPage;

    /**
     * {@link android.widget.ImageView} that shows a PDF page as a {@link Bitmap}
     */
    private PhotoView mImageView;

    /**
     * {@link Button} to move to the previous page.
     */
    private Button mButtonPrevious;

    /**
     * {@link Button} to move to the next page.
     */
    private Button mButtonNext;
    private Button mZoom;
private SeekBar mseekBar;
    /**
     * PDF page index
     */
    private int mPageIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_curse, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
        initlayout(view, savedInstanceState);
        try {
            openRenderer(getActivity());
            showPage(mPageIndex);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void initlayout(View view, Bundle savedInstanceState) {
        mImageView = (PhotoView) view.findViewById(R.id.photoView);
        mImageView.setZoomable(true);
        mButtonPrevious = (Button) view.findViewById(R.id.previous);
        mButtonNext = (Button) view.findViewById(R.id.next);
        mZoom=(Button)view.findViewById(R.id.zoom) ;
        mseekBar=(SeekBar) view.findViewById(R.id.seekBar) ;
        // Bind events.
        mButtonPrevious.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mZoom.setOnClickListener(this);

        mPageIndex = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            mPageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (null != mCurrentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
        }
        super.onSaveInstanceState(outState);
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable("android:support:fragments", null);
//    }

    /**
     * Sets up a {@link PdfRenderer} and related resources.
     */
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


    /**
     * Closes the {@link PdfRenderer} and related resources.
     *
     * @throws IOException When the PDF file cannot be closed.
     */
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

    /**
     * Shows the specified page of PDF to the screen.
     *
     * @param index The page index.
     */
    private void showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        mCurrentPage = mPdfRenderer.openPage(index);
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
    public void onClick(View view) {
        switch (view.getId()) {
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
                break;
            }
            case R.id.photoView:
//                mButtonNext.setVisibility(View.VISIBLE);
//                mButtonPrevious.setVisibility(View.VISIBLE);
                // 向左边移入

                if (teamp) {
                    mButtonPrevious.setVisibility(View.GONE);
                    mButtonNext.setVisibility(View.GONE);
                    mButtonNext.setVisibility(View.GONE);
                    mseekBar.setVisibility(View.GONE);
                    mZoom.setAnimation(AnimationUtil.outToBottomAnimation(getContext()));
                    mseekBar.setAnimation(AnimationUtil.outToBottomAnimation(getContext()));
                    mButtonPrevious.setAnimation(AnimationUtil.outToLeftAnimation());
                    mButtonNext.setAnimation(AnimationUtil.outToRightAnimation());
                    teamp = false;
                } else {
                    mButtonNext.setVisibility(View.VISIBLE);
                    mButtonPrevious.setVisibility(View.VISIBLE);
                    mZoom.setVisibility(View.VISIBLE);
                    mButtonNext.setAnimation(AnimationUtil.inFromRightAnimation());
                    mButtonPrevious.setAnimation(AnimationUtil.inFromLeftAnimation());
                    mZoom.setAnimation(AnimationUtil.inFromBottomAnimation(getContext()));
                    mseekBar.setAnimation(AnimationUtil.inFromBottomAnimation(getContext()));
                    teamp = true;
                }
        }
    }
//    @Override
//    public void onStop() {
//        Log.d("test","onStop");
//        try {
//            closeRenderer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        super.onStop();
//    }
}
