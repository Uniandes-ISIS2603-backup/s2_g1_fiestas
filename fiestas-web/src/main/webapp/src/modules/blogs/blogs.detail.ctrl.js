(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("blogsContext", "api/blogs");
    mod.controller('blogsDetailCtrl', ['$scope', '$http', 'blogsContext', '$state', '$filter', '$rootScope',
        /**
         * @ngdoc controller
         * @name blogs.controller:blogDetailCtrl
         * @description
         * Definición de un controlador auxiliar del módulo Bloges. 
         * Se crea el controlador con el cual se manejan las vistas de detalle
         * del módulo.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Bloges en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, blogsContext, $state, $filter, $rootScope) {
            
            
            /**
                 * @ngdoc function
                 * @name getBlogID
                 * @methodOf blogs.controller:blogDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el blog por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del blog o API donde se puede consultar.
                 */
            if (($state.params.blogsId !== undefined) && ($state.params.blogsId !== null)) {
                    $http.get(blogsContext).then(function (response) {
                    $scope.blogsRecords = response.data;
                    $scope.currentBlogs = $filter('filter')($scope.blogsRecords, {id: $state.params.blogsId}, true)[0];
                });
            }
        }
    ]);
}
        )(window.angular);