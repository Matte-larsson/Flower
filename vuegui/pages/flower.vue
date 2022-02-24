<template lang="jade">
<div>
<div><Nav></div>
  <div class="header">
  <h1>FlowerShop</h1>
  </div> 
  <div class="uheader">
  <h1>Add Flower</h1>
  </div>
  
  <div class="textfields">
  <label for="name">name</label>
  <input v-model="name" placeholder="enter name">
  <br>
  <br>
  <label for="price">price</label>
  <input v-model.number="price" placeholder="enter price">
  <br>
  <br>
  <label for="stock">stock</label>
  <input v-model.number="stock" placeholder="enter stock">
  <br>
  <br>
  <div><button :disabled="validInput" @click="createFlower()">Skapa</button></div>
</div>

  <div class="list">
  <div class="button">
  <ul id="array-rendering">
    <li v-for="f in flowers">
      {{ f.id }} - {{f.name}} -{{f.price}} - {{f.stock}} -  
      <span @click="deleteFlower(f.id)"> x </span> -
      <span @click="edit(f.id)"> edit </span>
    </li>
  </ul>
  </div>

  <style>
  .header {
    padding: 15px;
    text-align: center;
    color: black;
    font-size: 30px;
  }
    .uheader {
    padding: 10px 40px;
    text-align: left;
    color: black;
    font-size: 20px;
  }
  .textfields {
    padding: 40px;
    text-align: left;
    color: black;
    font-size: 25px;
  }
  .list {
    padding: 15px;
    text-align: left;
    color: black;
    font-size: 25px;
  }
  .button {
  cursor: pointer;
  outline: none;
}


  </style>
</template>

<script>
export default {
  data() {
    return {
      flowers: [],
      name: "",
      price: undefined,
      stock: undefined,
    };
  },
  computed: {
    validInput: function () {
      if (
        this.name != null &&
        this.name.length >= 3 &&
        this.price >= 1 &&
        this.stock >= 1
      ) {
        console.log("valid false");
        return false;
      } else return true;
    },
  },
  methods: {
    edit(id) {
      this.$router.push({
        path: "/edit/" + id,
      });
    },
    clearfields: function () {
      this.name = "";
      this.price = undefined;
      this.stock = undefined;
    },
    deleteFlower: function (fid) {
      this.$axios
        .delete("/flowers/" + fid)

        .then((response) => {
          this.fetchFlower();
        });
    },

    fetchFlower: function () {
      this.$axios
        .get("/flowers")
        .then((response) => (this.flowers = response.data.flowers));
    },
    createFlower: function () {
      this.$axios
        .post("/flowers", {
          name: this.name,
          price: this.price,
          stock: this.stock,
        })
        .then((response) => {
          this.fetchFlower();
          this.clearfields();
        });
    },
  },
  created: function () {
    this.fetchFlower();
  },
};
</script>