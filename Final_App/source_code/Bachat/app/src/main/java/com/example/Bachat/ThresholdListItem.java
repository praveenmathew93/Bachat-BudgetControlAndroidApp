package com.example.Bachat;

public class ThresholdListItem {
        private String Category;
        private String Amount;
        private String IconURL;


        public ThresholdListItem(String category, String amount , String iconURL) {
            Category = category;
            Amount = amount;
            IconURL=iconURL;

        }
        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getIconURL() { return IconURL; }

        public void setIconURL(String iconURL) { this.IconURL = iconURL; }

}
