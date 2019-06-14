package com.xingling.service.impl;

import com.xingling.constants.Constants;
import com.xingling.exception.BusinessException;
import com.xingling.service.TokenService;
import com.xingling.util.JedisUtil;
import com.xingling.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Resource
    private JedisUtil jedisUtil;

    @Override
    public String createToken() {
        String str = RandomUtil.UUID32();
        StringBuilder token = new StringBuilder();
        token.append(Constants.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constants.Redis.EXPIRE_TIME_MINUTE);

        return token.toString();
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new BusinessException("参数不合法");
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new BusinessException("请勿重复操作");
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new BusinessException("请勿重复操作");
        }
    }

}
