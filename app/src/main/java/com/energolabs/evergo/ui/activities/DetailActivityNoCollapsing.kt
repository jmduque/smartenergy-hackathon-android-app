package com.energolabs.evergo.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.energolabs.evergo.R

class DetailActivityNoCollapsing : DetailActivity() {

    override val layoutId: Int
        get() = R.layout.activity_detail_no_collapsing_toolbar

    override val fragmentLayoutId: Int
        get() = R.id.main_layout

    companion object {

        fun openWithFragment(
                context: Context,
                className: String,
                childBundle: Bundle?,
                withMemory: Boolean
        ) {
            context.startActivity(
                    makeIntent(
                            context,
                            className,
                            childBundle,
                            withMemory
                    )
            )
        }

        fun openWithFragmentForResult(
                activity: Activity,
                requestCode: Int,
                className: String,
                childBundle: Bundle,
                withMemory: Boolean
        ) {
            activity.startActivityForResult(
                    makeIntent(
                            activity,
                            className,
                            childBundle,
                            withMemory
                    ),
                    requestCode
            )
        }

        fun openWithFragmentForResult(
                fragment: Fragment,
                context: Context,
                requestCode: Int,
                className: String,
                childBundle: Bundle?,
                withMemory: Boolean
        ) {
            fragment.startActivityForResult(
                    makeIntent(
                            context,
                            className,
                            childBundle,
                            withMemory
                    ),
                    requestCode
            )
        }

        fun makeIntent(
                context: Context,
                className: String,
                childBundle: Bundle?,
                withMemory: Boolean
        ): Intent {
            val intent = Intent(
                    context,
                    DetailActivityNoCollapsing::class.java
            )

            val bundle = Bundle()
            bundle.putString(
                    DetailActivity.FRAGMENT_CLASS_NAME,
                    className
            )

            bundle.putParcelable(
                    DetailActivity.CHILD_BUNDLE,
                    childBundle
            )

            bundle.putBoolean(
                    DetailActivity.WITH_MEMORY,
                    withMemory
            )

            intent.putExtras(bundle)
            return intent
        }
    }
}
