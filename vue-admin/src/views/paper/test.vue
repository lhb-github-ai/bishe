<template>
    <div class="checkbox">

    <label   v-for="(item,index) in question_item"><input class="border_w" type="checkbox" :value="index" v-model="checkedValue"/>{{item.id}} </label> 

</div>
</template>
<script>

    export default {
        name:'checkbox',
        data(){
            return {
                checkedValue:[],//一定必须是数组不能是字符串
                answer:[],
                question_item:[{id:1},{id:2}]
            }
        },
  watch:{
        checkedValue:function(new_v,old_v){
            this.answer=this.checkedValue;
            console.log(  this.answer);
        }
    },
}

</script>