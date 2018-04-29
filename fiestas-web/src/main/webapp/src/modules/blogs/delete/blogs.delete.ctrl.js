(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("blogsContext", "api/blogs");
    mod.controller('blogsDeleteCtrl', ['$scope', '$http', 'blogsContext', '$state',
        /**
         * @ngdoc controller
         * @name blogs.controller:eventoDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} authorsContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, blogsContext, $state) {
            var idBlog = $state.params.blogsId;
            /**
             * @ngdoc function
             * @name deleteEvento
             * @methodOf blogs.controller:eventoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el evento.
             * @param {String} id El ID del evento a eliminar.
             */
            $scope.deleteBlogs = function () {
                $http.delete(blogsContext + '/' + idBlog, {}).then(function (response) {
                    $state.go('blogsList', {blogsId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);