package com.energolabs.evergo.modules.currencyWallet.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.energolabs.evergo.R;
import com.energolabs.evergo.modules.currencyWallet.controllers.CurrencyController;
import com.energolabs.evergo.ui.fragments.BaseFragment;

/**
 * Created by Jose Duque on 12/5/16.
 * Copyright (C) 2016 Energo Labs
 * <p/>
 * Copy or sale of this class is forbidden.
 */

public class WalletRechargeSuccessFragment
        extends
        BaseFragment
        implements
        View.OnClickListener {

    private static final String AMOUNT = "amount";
    private static final String CURRENCY_SYMBOL = "currency_symbol";

    public static final int CONTINUE = 2000;
    public static final int RETURN = 1000;

    // VIEWS
    private TextView tv_success_message;

    private View btn_continue_to_recharge;
    private View btn_return;

    // DATA
    private long amount;
    private String currencySymbol;

    public static Bundle makeArguments(
            long amount,
            String currencySymbol
    ) {
        Bundle args = new Bundle();
        args.putLong(
                AMOUNT,
                amount
        );
        args.putString(
                CURRENCY_SYMBOL,
                currencySymbol
        );
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            amount = args.getLong(AMOUNT);
            currencySymbol = args.getString(CURRENCY_SYMBOL);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet_recharge_success;
    }

    @Override
    protected void findViews(View view) {
        tv_success_message = (TextView) view.findViewById(R.id.tv_success_message);
        updateSuccessMessage();

        btn_continue_to_recharge = view.findViewById(R.id.btn_continue_to_recharge);
        btn_return = view.findViewById(R.id.btn_return);
    }

    private void updateSuccessMessage() {
        tv_success_message.setText(
                getResources().getString(
                        R.string.energo_wallet_recharge_success_message,
                        CurrencyController.INSTANCE.getRealValue(amount),
                        currencySymbol
                )
        );
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        btn_continue_to_recharge.setOnClickListener(this);
        btn_return.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.energo_wallet_recharge_success_title);
    }

    @Override
    protected void disableViews() {
        btn_return.setEnabled(false);
    }

    @Override
    protected void enableViews() {
        btn_return.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue_to_recharge: {
                getActivity().setResult(CONTINUE);
                getActivity().finish();
                break;
            }
            case R.id.btn_return: {
                getActivity().setResult(RETURN);
                getActivity().finish();
                break;
            }
        }
    }

}
