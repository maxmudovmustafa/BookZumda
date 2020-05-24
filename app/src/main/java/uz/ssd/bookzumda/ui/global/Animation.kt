/*
 * Copyright (C) 2019 Xizhi Zhu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uz.ssd.bookzumda.ui.global

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

private const val ANIMATION_DURATION = 300L

fun View.fadeIn() {
    if (visibility == View.VISIBLE) {
        alpha = 1.0F
        return
    }

    alpha = 0.0F
    visibility = View.VISIBLE
    animate().alpha(1.0F).setDuration(ANIMATION_DURATION)
        .setListener(null)
}

fun View.fadeOut(isCanceled: () -> Boolean = { false }) {
    if (visibility == View.GONE) {
        return
    }

    alpha = 1.0F
    animate().alpha(0.0F).setDuration(ANIMATION_DURATION)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val canceled = isCanceled.invoke()
                if (canceled.not()) {
                    visibility = View.GONE
                    alpha = 1.0F
                }
            }
        })
}