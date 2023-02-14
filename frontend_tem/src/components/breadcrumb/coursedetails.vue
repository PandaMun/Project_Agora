<template>
<!-- Breadcrumb -->
<div class="breadcrumb-bar">
    <div class="container">
        <div class="row">
            <div class="col-md-12 col-12">
                <div class="breadcrumb-list">
                    <nav aria-label="breadcrumb" class="page-breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
                            <li class="breadcrumb-item" aria-current="page">Debates</li>
                            <li class="breadcrumb-item" aria-current="page">All Debates</li>
                            <li class="breadcrumb-item active" aria-current="page">{{ debate.title }}</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /Breadcrumb -->
</template>
<script>
    export default {
        props: ["debate"],
    };
</script>
