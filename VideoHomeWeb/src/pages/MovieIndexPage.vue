<template>
  <q-page padding>
<!--    提示条幅  -->
    <div class="q-pb-sm lt-sm" v-if="showTip">
      <q-banner inline-actions  class="bg-orange text-white q-py-none">
        <span class="text-caption">提示：若手机端播放时没有声音，可以尝试使用其他浏览器进行播放
        </span>
        <template v-slot:action>
          <q-btn round dense flat icon="mdi-close" @click="closeTip()"/>
        </template>
      </q-banner>
    </div>
<!--    类型标签  -->
    <div class="row q-mb-md">
      <div class="col-12">
        <q-chip clickable :label="item.label" :class="{'text-bold':item.value==='全部'}"
               :color="selectedType===item.value?'primary':''"
               :text-color="selectedType===item.value?'white':''"
               @click="selectType(item.value)"
               v-for="item in movieTypeList" :key="item.label"/>
      </div>
    </div>
<!--    排序与搜索  -->
    <div class="row q-mb-md q-col-gutter-md">
      <div class="col-xs-12 col-sm-9">
        <q-btn flat :label="item.label"
               :color="selectedSort===item.value?'blue':'grey'"
               @click="selectSort(item.value)"
               v-for="item in sortList" :key="item.label"/>
      </div>
      <div class="col-xs-12 col-sm-3" >
        <q-input dense outlined v-model.trim="searchText">
          <template v-slot:append>
            <q-icon v-if="searchText !== ''" name="mdi-close" @click="searchText = ''" class="cursor-pointer" ></q-icon>
            <q-btn unelevated color="primary"  icon="mdi-magnify" style="margin-right: -12px" @click="search()"></q-btn>
          </template>
        </q-input>
      </div>
    </div>
    <div class="row q-my-md" v-if="searchText !== ''">
      <div class="col-12">
        <span class="text-grey">搜索到 {{filteredMovies.length}} 部影视：</span>
      </div>
    </div>

<!--    电影卡片部分 -->
    <div class="row q-col-gutter-md">
      <div class="col-xs-6 col-sm-3 col-md-2" v-for="item in filteredMovies" :key="item.name">
        <a :href='"/ui/movie/"+item.name' target="_blank" style="text-decoration: none; color:black">
          <q-card flat class="video-card">
            <q-card-section class="q-pa-none">
              <img :src="item.poster!=null?http.ServerAddress+item.poster:'/默认海报.png'" class="poster-img"/>
              <span class="score">{{ (item.score+"").includes(".") ? item.score : item.score+".0" }}</span>
            </q-card-section>
            <q-card-section class="q-py-xs" style="text-align: center">
              <div class="text-body2 text-weight-bold" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{ item.name}}</div>
            </q-card-section>
          </q-card>
        </a>
      </div>
    </div>

<!--    回到顶部按钮  -->
    <q-page-scroller position="bottom-right" :scroll-offset="150" :offset="[18, 18]">
      <q-btn fab icon="mdi-chevron-up" color="primary" ></q-btn>
    </q-page-scroller>

  </q-page>

</template>

<script setup>
import http from 'src/api/http.js'
import { ref, computed, onMounted } from "vue";
import { useRouter } from 'vue-router'
import {useQuasar} from "quasar";
const router = useRouter()
const $q = useQuasar()

const movieTypeList= [
  {label: '全部', value: '全部'},
  {label: '剧情', value: '剧情'},
  {label: '喜剧', value: '喜剧'},
  {label: '爱情', value: '爱情'},
  {label: '动作', value: '动作'},
  {label: '科幻', value: '科幻'},
  {label: '动画', value: '动画'},
  {label: '奇幻', value: '奇幻'},
  {label: '悬疑', value: '悬疑'},
  {label: '犯罪', value: '犯罪'},
  {label: '恐怖', value: '恐怖'},
  {label: '惊悚', value: '惊悚'},
  {label: '灾难', value: '灾难'},
  {label: '冒险', value: '冒险'},
  {label: '历史', value: '历史'},
  {label: '战争', value: '战争'},
  {label: '传记', value: '传记'},
  {label: '纪录', value: '纪录'},
]
const sortList=[
  {label: '最近上映', value: '最近上映'},
  {label: '最高评分', value: '最高评分'},
  {label: '随机排序', value: '随机排序'},
]
const selectedType= ref('全部')
const selectedSort=ref('最近上映')
const searchText=ref('')
const showTip = ref(true)
const movies = ref([])

function closeTip() {
  showTip.value = false
  $q.localStorage.set('showTip', showTip.value)
}

function selectType(type){
  selectedType.value = type
}
function selectSort(sort){
  selectedSort.value = sort
}
function search(){
  if(searchText.value !== ''){
    selectedType.value = '全部'
    selectedSort.value = '最近上映'
  }
}

const filteredMovies = computed(() => {
  let list = []
  movies.value.forEach(item=>{
    if(searchText.value===''||item.name.includes(searchText.value)){
      if(selectedType.value==='全部'||item.types.indexOf(selectedType.value)!==-1){
        list.push(item)
      }
    }
  })
  if(selectedSort.value==='最近上映'){
    list.sort(function (a,b) {
      return b.year - a.year
    })
  }else if(selectedSort.value==='最高评分'){
    list.sort(function (a,b) {
      return b.score - a.score
    })
  }else if(selectedSort.value==='随机排序'){
    list.sort(function () {
      return Math.random() - 0.5
    })
  }
  return list
})

function loadData() {
  http.getAllMovies().then(res=>{
    movies.value = res.data
  })
}

onMounted(()=>{
  showTip.value = $q.localStorage.getItem('showTip')
  if(showTip.value===null){
    showTip.value = true
  }
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
.score {
  position: absolute;
  top: 0;
  right: 0;
  padding: 2px 8px;
  font-size: 12px;
  border-radius: 0 0 0 10px;
  background-color: #FF9800;
  color: #FFF;
}
</style>
