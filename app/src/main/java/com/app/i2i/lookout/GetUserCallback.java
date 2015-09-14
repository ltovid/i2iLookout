package com.app.i2i.lookout;


interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    void done(com.app.i2i.lookout.User returnedUser);
}

