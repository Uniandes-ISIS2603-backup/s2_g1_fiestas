(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("blogsContext", "api/blogs");
    mod.controller('blogsUpdateCtrl', ['$scope', '$http', 'blogsContext', '$state', '$rootScope',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, blogsContext, $state, $rootScope) {

            $scope.data = {};
            
            $rootScope.edit=true;

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idBlog = $state.params.blogsId;

            //Consulto el evento a editar.
            $http.get(blogsContext + '/' + idBlog).then(function (response) {
                var blog = response.data;
                $scope.data.cuerpo = blog.cuerpo;
                $scope.data.likes = blog.likes;
                $scope.data.titulo = blog.titulo;
            });

            /**
             * @ngdoc function
             * @name updateEvento
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Actualiza un evento con la información del
             * $scope.
             */
            $scope.updateBlogs = function () {
                $http.put(blogsContext + "/" + idBlog, $scope.data).then(function (response) {
                    //Evento created successfully
                    $state.go('blogsList', {blogsId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


