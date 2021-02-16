<template>
	<view>
		<view>{{username}}</view>
		<view v-for="item in tel">{{item}}</view>
		<view v-if="age>19">
			<button @tap="study">我要学习</button>
		</view>
		<input type="text" v-model="message" placeholder="输入message">
		<view>{{message}}</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username:"cgcg",
				tel:["123","456"],
				age:30,
				message:""
			}
		},
		methods: {
			study(){
				uni.showToast({
					title:"点击了报名按钮"
				})
			}
		}
	}
</script>

<style>

</style>
