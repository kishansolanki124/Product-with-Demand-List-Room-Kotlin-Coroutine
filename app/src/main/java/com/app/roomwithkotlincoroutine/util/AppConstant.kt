package com.app.roomwithkotlincoroutine.util

class AppConstant {
    companion object {
        const val ACTIVITY_RESULT_CODE_100 = 100
        const val ADD_DEMAND = "ADD_DEMAND"
        const val PRODUCT_LIST = "PRODUCT_LIST"
    }

    interface DiscountTYpe{
        companion object{
            const val NONE= "none"
            const val PERCENTAGE= "percentage"
            const val AMOUNT= "amount"
        }
    }

    interface DemandStatus{
        companion object{
            const val IN_PROGRESS= "In-Progress"
        }
    }
}