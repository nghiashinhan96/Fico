<template>
  <div>
    <el-card :body-style="{ padding: '5px' }">
      <el-input
        placeholder="Search"
        v-model="valueSearch"
        style="width: 500px"
        @keyup.enter.native="handleSearch"
      >
        <el-select v-model="keySearch" slot="prepend" placeholder="Key" style="width: 150px">
          <el-option label="App ID" value="appId"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
      </el-input>
    </el-card>
    <tpf-table-momo
      v-loading="state.momo.MomoDataentyAss.isLoading"
      :data="state.momo.MomoDataentyAss.list"
      :headers="headers.assigned"
      department="MomoDataenty"
      :assigned="true"
      title="Assigned"
    ></tpf-table-momo>
    <el-card :body-style="{ padding: '5px' }">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="params.page"
        :page-sizes="[5, 10, 20,]"
        :page-size="params.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="parseInt(state.momo.MomoDataentyAss.total)"
      ></el-pagination>
    </el-card>

    <tpf-table-momo
      v-loading="state.momo.MomoDataentyUnAss.isLoading"
      :data="state.momo.MomoDataentyUnAss.list"
      :headers="headers.unassigned"
      department="MomoDataenty"
      :assigned="false"
      title="UnAssigned"
    ></tpf-table-momo>
    <el-card :body-style="{ padding: '5px' }">
      <el-pagination
        @size-change="handleSizeChangeUnAss"
        @current-change="handleCurrentChangeUnAss"
        :current-page.sync="paramsUnAss.page"
        :page-sizes="[10, 20, 30]"
        :page-size="paramsUnAss.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="parseInt(state.momo.MomoDataentyUnAss.total)"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
import TpfTableMomo from "./components/MomoTable";
import axios from "axios";
export default {
  name: "Automation",
  components: { TpfTableMomo },
  data() {
    return {
      keySearch: "appId",
      valueSearch: "",
      loadingNow: true,
      params: {
        page: 1,
        limit: 5
      },
      paramsUnAss: {
        page: 1,
        limit: 10
      }
    };
  },
  props: {},
  methods: {
    handleSearch() {
      this.state.momo.MomoDataentyAss._search[
        this.keySearch
      ] = this.valueSearch;
      this.$store.dispatch("momo/fnCallListView", "MomoDataentyAss");
      this.state.momo.MomoDataentyUnAss._search[
        this.keySearch
      ] = this.valueSearch;
      this.$store.dispatch("momo/fnCallListView", "MomoDataentyUnAss");
    },
    handleSizeChange(a) {
      this.params.limit = a;
      this.getList();
    },
    handleCurrentChange(a) {
      this.params.page = a;
      // if (a > 1 && this.state.momo.MomoDataentyUnAss.total % this.params.page == 0) {
      //   this.state.momo.MomoDataentyUnAss._page = 1
      // }
      this.getList();
    },

    handleSizeChangeUnAss(a) {
      this.paramsUnAss.limit = a;
      this.getList("UnAss");
    },
    handleCurrentChangeUnAss(a) {
      this.paramsUnAss.page = a;
      this.getList("UnAss");
    },
    getList(v) {
      if (v != "UnAss") {
        this.state.momo.MomoDataentyAss = {
          ...this.state.momo.MomoDataentyAss,
          _page: this.params.page,
          rowsPerPage: this.params.limit,
          _search: {
            project: "momo",
            department: "data_entry",
            assigned: this.fnCookie().getInforUser().user_name
          }
        };
        this.$store.dispatch("momo/fnCallListView", "MomoDataentyAss");
      } else {
        this.state.momo.MomoDataentyUnAss = {
          ...this.state.momo.MomoDataentyUnAss,
          _page: this.paramsUnAss.page,
          rowsPerPage: this.paramsUnAss.limit,
          _search: {
            project: "momo",
            department: "data_entry",
            assigned: ""
          }
        };
        this.$store.dispatch("momo/fnCallListView", "MomoDataentyUnAss");
      }
    },
  },

  computed: {},

  created() {
    this.headers = {
      assigned: [
        {
          text: "CREATED DATE",
          align: "left",
          value: "createdAt",
          sortable: true,
          width: "180px"
        },
        {
          text: "APP ID",
          align: "left",
          value: "appId",
          sortable: true,
          width: "180px"
        },
        {
          text: "CUSTOMER ID",
          align: "left",
          value: "partnerId",
          sortable: true,
          width: "180px"
        },
        { text: "FULL NAME", align: "left", value: "fullName", width: "300px" },
        {
          text: "STATUS",
          align: "left",
          value: "automationResult",
          sortable: true,
          width: "150px"
        },
        {
          text: "Application",
          align: "center",
          value: "id",
          sortable: false,
          width: "150px"
        },
        {
          text: "UNASSIGNED",
          align: "center",
          value: "assigned",
          sortable: false,
          width: "120px"
        },
        {
          text: "FIX MANUALY",
          align: "center",
          value: "fixmanualy",
          sortable: false,
          width: "120px"
        }
        // {
        //   text: "RETRY",
        //   align: "center",
        //   value: "retry",
        //   sortable: false,
        //   width: "120px"
        // }
      ],
      unassigned: [
        {
          text: "CREATED DATE",
          align: "left",
          value: "createdAt",
          sortable: true,
          width: "180px"
        },
        {
          text: "APP ID",
          align: "left",
          value: "appId",
          sortable: true,
          width: "180px"
        },
        {
          text: "CUSTOMER ID",
          align: "left",
          value: "partnerId",
          sortable: true,
          width: "180px"
        },
        { text: "FULL NAME", align: "left", value: "fullName", width: "300px" },
        {
          text: "STATUS",
          align: "left",
          value: "automationResult",
          sortable: true,
          width: "150px"
        },
        {
          text: "Application",
          align: "center",
          value: "id",
          sortable: false,
          width: "150px"
        },
        {
          text: "ASSIGNED",
          align: "center",
          value: "assigned",
          sortable: false,
          width: "120px"
        }
      ]
    };

    this.state.momo.MomoDataentyAss = {
      ...this.state.momo.MomoDataentyAss,
      _page: this.params.page,
      rowsPerPage: this.params.limit,
      _search: {
        project: "momo",
        department: "data_entry",
        assigned: this.fnCookie().getInforUser().user_name
      }
    };

    this.state.momo.MomoDataentyUnAss = {
      ...this.state.momo.MomoDataentyUnAss,
      _page: this.paramsUnAss.page,
      rowsPerPage: this.paramsUnAss.limit,
      _search: {
        project: "momo",
        department: "data_entry",
        assigned: ""
      }
    };

    this.$store.dispatch("momo/fnCallListView", "MomoDataentyAss");

    this.$store.dispatch("momo/fnCallListView", "MomoDataentyUnAss");
  }
};
</script>

<style>
.data-table {
  margin-top: 10px;
  margin-left: 0px;
  margin-right: 0px;
}
.boody {
  margin-top: 10px;
  height: 97%;
  margin-left: 10px;
  margin-right: 10px;
}
.pagination-container {
  background: #fff;
  margin-top: 10px;
}
</style>
