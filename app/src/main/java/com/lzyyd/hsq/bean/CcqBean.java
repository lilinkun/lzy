package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqBean {
    private CcqListBean Ccq;

    public CcqListBean getCcq() {
        return Ccq;
    }

    public void setCcq(CcqListBean ccq) {
        Ccq = ccq;
    }

    public class CcqListBean{
        private int CcqNoUse;
        private int CcqUse;

        public int getCcqNoUse() {
            return CcqNoUse;
        }

        public void setCcqNoUse(int ccqNoUse) {
            CcqNoUse = ccqNoUse;
        }

        public int getCcqUse() {
            return CcqUse;
        }

        public void setCcqUse(int ccqUse) {
            CcqUse = ccqUse;
        }
    }
}
