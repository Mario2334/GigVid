package com.android.gigvid.view.homescreen.fragment;

import android.database.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile.BankDetailResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile.BankDetailsReqBody;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.viewModel.homescreen.ProfileViewModel;
import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextInputLayout userNameTxt;
    private TextInputLayout bankAccNum;
    private TextInputLayout ifscCodeTxt;
    private Button bankDetailSubmit;

    private Observer<DataResponse<BankDetailResp>> mBankAccDetails = new Observer<DataResponse<BankDetailResp>>() {
        @Override
        public void onChanged(DataResponse<BankDetailResp> bankDetailRespDataResponse) {
            if (bankDetailRespDataResponse.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("bank details available %d", bankDetailRespDataResponse.getData().getBalance());
                userNameTxt.getEditText().setText(bankDetailRespDataResponse.getData().getName());
                userNameTxt.getEditText().setActivated(false);
                ifscCodeTxt.getEditText().setText(bankDetailRespDataResponse.getData().getIfsc());
                ifscCodeTxt.getEditText().setActivated(false);
                bankAccNum.getEditText().setText(bankDetailRespDataResponse.getData().getAccountNo());
                bankAccNum.getEditText().setActivated(false);

                bankDetailSubmit.setVisibility(View.GONE);
            } else {
                Timber.d("bank detail not available ");
                bankDetailSubmit.setVisibility(View.VISIBLE);
            }
        }
    };


    private Observer<DataResponse<String>> mBankDetailObserver = new Observer<DataResponse<String>>() {
        @Override
        public void onChanged(DataResponse<String> data) {
            if (data.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("success available %s", data.getData());
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Bank added", Toast.LENGTH_SHORT).show();
            } else {
                Timber.d("bank detail save fail ");
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void initializeViews(View view){
        userNameTxt = view.findViewById(R.id.bank_user_name);
        bankAccNum = view.findViewById(R.id.user_bank_account_number);
        ifscCodeTxt = view.findViewById(R.id.user_bank_ifsc_code);

        bankDetailSubmit = view.findViewById(R.id.add_bank_button);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        profileViewModel.getBankDetails().observe(this, mBankAccDetails);


        initializeViews(root);

        bankDetailSubmit.setOnClickListener(submitBtnClick);
        return root;
    }


    private View.OnClickListener submitBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String name = userNameTxt.getEditText().getText().toString();
            String ifsc = ifscCodeTxt.getEditText().getText().toString();
            String accNum = bankAccNum.getEditText().getText().toString();

            if(name.isEmpty() || ifsc.isEmpty() || accNum.isEmpty()){
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Enter all details", Toast.LENGTH_SHORT).show();
            } else{
                BankDetailsReqBody bankDetailsReqBody = new BankDetailsReqBody(name, ifsc, accNum);
                profileViewModel.addBankDetails(bankDetailsReqBody).observe(ProfileFragment.this,mBankDetailObserver );
            }

        }
    };

}
