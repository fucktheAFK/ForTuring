<template>
    <nav class="navbar">
        <h1>HRM 系统</h1>
        <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/employees">员工管理</router-link></li>
        </ul>
    </nav>
</template>

<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: "Navbar",
    });
</script>

<style>
    .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #3b82f6;
        padding: 10px 20px;
        color: white;
    }

        .navbar ul {
            display: flex;
            list-style: none;
            gap: 15px;
        }

        .navbar a {
            color: white;
            text-decoration: none;
        }
</style>
