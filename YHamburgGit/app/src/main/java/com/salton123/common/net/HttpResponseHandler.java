package com.salton123.common.net;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/22 12:19
 * Time: 12:19
 * Description:
 */

public interface HttpResponseHandler<T> {
    public void onSuccess(T content);

    public void onFailure(String content);
}
