package ocrm.labex.feevale.br.ocr_mobile;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.TestCase;

import ocrm.labex.feevale.br.ocr_mobile.model.Image;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.EditableTextFragment;
import ocrm.labex.feevale.br.ocr_mobile.view.fragments.ImageTextFragment;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    MainActivity activity;
    Fragment fragment;
    Image image;
    Fragment f;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
         Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MainActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
        image = new Image("macaco","macaco.png","Testando o teste que testa o que está sendo testado!");
    }

    public void testChangeFragment() throws Exception {

        getInstrumentation().stopProfiling();
        fragment = new EditableTextFragment(activity,image);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().changeFragment(fragment);
                getInstrumentation().callActivityOnRestart(getActivity());
                activity = getActivity();


                f = getActivity().getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
                activity = getActivity();

                assertNotNull(activity);
                assertNotNull(f);
                getInstrumentation().startProfiling();
                View newView = f.getView();
                ImageView imageView = (ImageView) newView.findViewById(R.id.imageSaved);

                assertNotNull(imageView);
                EditText editText = (EditText)newView.findViewById(R.id.editTextImage);

                String oldText = editText.getText().toString();
                assertEquals(oldText, editText.getText().toString());

                assertEquals(oldText, editText.getText().toString());
                getInstrumentation().stopProfiling();
            }
        });



    }
/*
    public void testChangeFragmentImageText() throws Exception {
        fragment = new ImageTextFragment(activity,image);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().changeFragment(fragment);
                getInstrumentation().callActivityOnRestart(getActivity());
                activity = getActivity();
                f = getActivity().getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
            }
        });
        assertNotNull(activity);
        assertNotNull(f);
        getInstrumentation().startProfiling();
        View newView = f.getView();
        ImageView imageView = (ImageView) newView.findViewById(R.id.imageItem);

        assertNotNull(imageView);
        TextView text = (TextView)newView.findViewById(R.id.textItem);

        assertEquals(text.getText(), image.getText());

        getInstrumentation().stopProfiling();
    }*/
}