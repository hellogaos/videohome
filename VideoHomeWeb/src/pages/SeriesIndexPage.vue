<template>
  <q-page padding>
    <!--    排序与搜索  -->
    <div class="row q-mb-md q-col-gutter-md">
      <q-space/>
      <div class="col-xs-12 col-sm-3" >
        <q-input dense outlined v-model.trim="searchText">
          <template v-slot:append>
            <q-icon v-if="searchText !== ''" name="mdi-close" @click="searchText = ''" class="cursor-pointer" ></q-icon>
            <q-btn unelevated color="primary"  icon="mdi-magnify" style="margin-right: -12px"></q-btn>
          </template>
        </q-input>
      </div>
    </div>
    <div class="row q-my-md" v-if="searchText !== ''">
      <div class="col-12">
        <span class="text-grey">搜索到 {{filteredSeries.length}} 部影视：</span>
      </div>
    </div>
    <!--    剧集卡片部分 -->
    <div class="row q-col-gutter-md">
      <div class="col-xs-6 col-sm-3 col-md-2" v-for="item in filteredSeries" :key="item.name">
        <a :href='"/ui/series/"+item.name' target="_blank" style="text-decoration: none; color:black">
          <q-card flat class="video-card">
            <q-card-section class="q-pa-none">
              <img :src="item.poster!=null?http.ServerAddress+item.poster:'/默认海报.png'" class="poster-img"/>
            </q-card-section>
            <q-card-section class="q-py-xs" style="text-align: center">
              <div class="text-body2 text-weight-bold" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{ item.name}}</div>
            </q-card-section>
          </q-card>
        </a>
      </div>
    </div>
    <q-page-scroller position="bottom-right" :scroll-offset="150" :offset="[18, 18]">
      <q-btn fab icon="mdi-chevron-up" color="primary" ></q-btn>
    </q-page-scroller>
  </q-page>
</template>

<script setup>
import http from 'src/api/http.js'
import {ref, onMounted, computed} from "vue";
import { useRouter } from 'vue-router'
const router = useRouter()
const series = ref([])
const searchText=ref('')


const filteredSeries = computed(() => {
  let list = []
  series.value.forEach(item=>{
    if(searchText.value===''||item.name.includes(searchText.value)){
      list.push(item)
    }
  })
  return list
})


function loadData() {
  http.getAllSeries().then(res=>{
    series.value = res.data
  })
}
onMounted(()=>{
  loadData()
})

</script>

<style scoped>
.video-card {
  box-shadow: 0 1px 5px #0003, 0 2px 2px #00000024, 0 3px 1px -2px #0000001f !important;
  transition: all 0.3s;
}
.video-card:hover{
  box-shadow: 0 8px 10px -5px #0003,0 16px 24px 2px #00000024,0 6px 30px 5px #0000001f !important;
  transform: translateY(-5px);
  cursor: pointer;
}
.poster-img{
  border-radius: 4px 4px 0 0;
  height: 230px;
  width:100%;
  object-fit: cover;
}
</style>
