<template>
  <el-carousel :interval="5000" arrow="always">
    <el-carousel-item v-for="(item, i) in items" :key="item+i">
<<<<<<< HEAD
      <!-- <slot name="error">
        <div class="el-image__error">{{ "Image Not Found" }}</div>
      </slot> -->
      <el-image style="width: 100%; height: 100%" fit="scale-down" :src="item.viewUrl"></el-image>
=======
      <el-image style="width: 100%; height: 100%" fit="scale-down" :src="item.viewUrl">
        <span class="el-image__error" slot="error"> Image Not Found </span>
      </el-image>
>>>>>>> 3d209ce3af6e9c282e4ea9a31a702480da7a2ee3
    </el-carousel-item>
  </el-carousel>
</template>
<script>
export default {
  name: "tpf-image-slide",
  data() {
    return {};
  },
  props: {
    items: { type: Array }
  }
};
</script>
<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.el-image__error {
  font-size: 39px;
  color: goldenrod;
}
</style>