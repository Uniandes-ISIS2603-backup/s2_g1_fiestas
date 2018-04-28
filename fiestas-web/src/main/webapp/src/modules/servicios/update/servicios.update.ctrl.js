(function (ng) {
    var mod = ng.module("servicioModule");
    mod.constant("serviciosContext", "api/servicio");

    mod.controller('servicioUpdateCtrl', ['$scope', '$http', 'serviciosContext', '$state', '$rootScope',
        
        function ($scope, $http, serviciosContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            var idServicio = $state.params.servicioId;

            //Consulto el autor a editar.
            $http.get(serviciosContext + '/' + idServicio).then(function (response) {
                var servicio = response.data;
                
                $scope.data.nombre = servicio.nombre;
                $scope.data.descripcion = servicio.descripcion;
                $scope.data.tipo = servicio.tipo;
            });
            
            /**
             * @ngdoc function
             * @name createProveedor
             * @methodOf proveedores.controller:proveedorUpdateCtrl
             * @description
             * Crea un nuevo autor con los libros nuevos y la información del
             * $scope.
             */
            $scope.updateServicio = function () {
                $http.put(serviciosContext + "/" + idServicio, $scope.data).then(function (response) {
                    $state.go('serviciosList', {servicioId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);