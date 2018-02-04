package com.khwebgame.core.protocol;

public interface RoomProtocol {
    public enum TYPE {
        TEST(0);

        private int val;
        TYPE(int _val) {
            val = _val;
        }

        public int getVal() {
            return val;
        }
    }
}
