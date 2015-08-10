package com.app.i2i.lookout;


interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(com.app.i2i.lookout.User returnedUser);
}

