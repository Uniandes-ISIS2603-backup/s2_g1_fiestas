(function (ng) {
    var mod = ng.module("productoModule");
    mod.constant("productosContext", "api/productos");
    mod.constant("booksContext", "api/books");
    mod.controller('productoUpdateCtrl', ['$scope', '$http', 'productosContext', '$state', 'booksContext', '$rootScope', '$filter',
        /**
         * @ngdoc controller
         * @name productos.controller:productoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} productosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} booksContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Libros en el Backend.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, productosContext, $state,  $rootScope, $filter) {
            $rootScope.edit = true;

            var idProducto = $state.params.productoId;
            
            var idServicio = $state.params.servicioId;

            //Consulto el autor a editar.
            $http.get(productosContext + '/' + idProducto).then(function (response) {
                var producto = response.data;
                $scope.productoName = producto.name;
                $scope.productoDescripcion = producto.description;
                $scope.productoPersonal = producto.personal;
                $scope.prodcutoIncluye = producto.incluye;
                $scope.prodcutoPrecio = producto.precio;
            });

            //funciones para el drag and drop de HTML5 nativo
            $scope.allowDrop = function (ev) {
                ev.preventDefault();
            };

            $scope.drag = function (ev) {
                ev.dataTransfer.setData("text", ev.target.id);
            };

            $scope.dropAdd = function (ev) {
                ev.preventDefault();
                var data = ev.dataTransfer.getData("text");
                ev.target.appendChild(document.getElementById(data));
                //Cuando un book se añade al autor, se almacena su id en el array idsBook
                idsBook.push("" + data);
            };

            $scope.dropDelete = function (ev) {
                ev.preventDefault();
                var data = ev.dataTransfer.getData("text");
                ev.target.appendChild(document.getElementById(data));
                //Para remover el book que no se va asociar, por eso se usa el splice que quita el id del book en el array idsBook
                var index = idsBook.indexOf(data);
                if (index > -1) {
                    idsBook.splice(index, 1);
                }
            };


            /**
             * @ngdoc function
             * @name createProducto
             * @methodOf productos.controller:productoUpdateCtrl
             * @description
             * Crea un nuevo autor con los libros nuevos y la información del
             * $scope.
             */
            $scope.createProducto = function () {
                /*Se llama a la función newBooks() para buscar cada uno de los ids de los books
                 en el array que tiene todos los books y así saber como queda la lista final de los books asociados al autor.
                 */
                $scope.newBooks();
                $http.put(productosContext + "/" + idProducto, {
                    name: $scope.productoName,
                    birthDate: $scope.productoBirthDate,
                    description: $scope.productoDescription,
                    image: $scope.productoImage
                }).then(function (response) {
                    if (idsBook.length >= 0) {
                        $http.put(productosContext + "/" + response.data.id + "/books", $scope.allBooksProducto).then(function (response) {
                        });
                    }
                    //Producto created successfully
                    $state.go('productosList', {productoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);