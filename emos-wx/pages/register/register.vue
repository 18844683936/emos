<template>
    <view>
        <image src="../../static/logo-2.png" mode="widthFix" class="logo"></image>
        <view class="register-container">
            <input placeholder="输入你的邀请码" class="register-code" maxlength="6" v-model="registerCode" />
            <view class="register-desc">管理员创建员工证账号之后，你可以从你的个人邮箱中获得注册邀请码</view>
            <button class="register-btn" open-type="getUserInfo" @tap="register()">执行注册</button>
        </view>
    </view>
</template>
<script>
    export default {
        data() {
            return {
                registerCode: null
            };
        },
        methods: {
			register(){
				uni.login({
				  provider: 'weixin',
				  success: function (resp) {
				    console.log(resp.code);
				  }
				});
			}
        }
    };
</script>
<style lang="less">
    @import url('register.less');
</style>