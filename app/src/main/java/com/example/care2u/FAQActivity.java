package com.example.care2u;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.care2u.adapter.FAQAdapter;
import com.example.care2u.entity.FAQ;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    private List<FAQ> faqList = new ArrayList<>();
    private ListView faqListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);
        faqListView = findViewById(R.id.faqs_listview);

        ImageView back_button = findViewById(R.id.IV_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initPage();
    }

    private void initPage() {
        faqList.add(new FAQ("What services does Care2U app provide?",
                "Our services include chat-consultation of a locally-licensed practicing doctor within minutes anytime, anywhere, with medication delivered to doorsteps." +
                        " We also provide a appointment system for user to book their convenience time for consulting. Included BMI and depression test to evaluate your health." +
                        " A meditation function to help user to release their stress. "
                        ));

        faqList.add(new FAQ("Who can use Care2U app?","Anyone with non-urgent medical conditions is suitable." +
                "Patients below the age of 16 years old must be accompanied by a parent during the consultation."));

        faqList.add(new FAQ("Is my personal data safe on the platform?","Yes, we take confidentiality very seriously. " +
                "Our platform employs end-to-end encryption to keep your information secure."));

        faqList.add(new FAQ("How will the medications be delivered?","After confirming your medications, your medications will be delivered to your preferred address."));

        faqList.add(new FAQ("Are there any fees or surcharges for medication delivery?","Medication delivery is free-of-charge. However, kindly note that there will be redelivery surcharges if you miss the initial delivery."));

        faqList.add(new FAQ("How do I update my details on the app?","To update your details, enter the app and click on the \"Profile\" tab. Next, click on the \"Edit Profile\" tab and select the item that you would like to update. " +
                "Once you have updated it, click on the \"Save\" button and your details will be updated."));

        faqList.add(new FAQ("How do I cancel a scheduled appointment?","Once you have booked an appointment, you will not be able to cancel it. However, you can reschedule it to another available time slot. " +
                "If you need to reschedule your appointment."));

        faqList.add(new FAQ("How do I purchase my medication after the consultation?","After the consultation, you will receive a notification when your medications are ready for purchase." +
                " Click on this notification to enter the app and review your prescribed medication list."));

        faqList.add(new FAQ("Are there any fees or surcharges for depression test?","It is free. You can do the test for many times."));

        faqList.add(new FAQ("Is the meditation function free to use?","Yes, it is free to use."));

        FAQAdapter faQsAdapter = new FAQAdapter(this,faqList);
        faqListView.setAdapter(faQsAdapter);

    }
}