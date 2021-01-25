# SimpleDialogs
My first experimental Android library for dialogs

How to use:

    val dialog = SingleSelectDialogFragment.Builder()
        .title(getString(R.string.title))
        .options(listOf("Option 1", "Option 2", "Option 3"))
        .onSelectedListener(object : SingleSelectDialogFragment.Companion.OnSelectedListener {
            override fun onSelected(item: Pair<String, Int>) {  
                val selectedOptionName = item.first
                val selectedIndex = item.second
                // Hide dialog after option is selected
                if(dialog.isVisible) dialog.dismiss()
            }
        }).build()
