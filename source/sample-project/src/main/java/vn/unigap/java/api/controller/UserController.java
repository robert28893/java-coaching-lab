package vn.unigap.java.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.java.api.dto.in.PageDtoIn;
import vn.unigap.java.api.dto.in.UpdateUserDtoIn;
import vn.unigap.java.api.dto.in.UserDtoIn;
import vn.unigap.java.api.dto.out.PageDtoOut;
import vn.unigap.java.api.dto.out.UserDtoOut;
import vn.unigap.java.api.service.UserService;
import vn.unigap.java.common.controller.AbstractResponseController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Quản lý user")
public class UserController extends AbstractResponseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Lấy danh sách users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageUser.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> list(@Valid PageDtoIn pageDtoIn) {
        return responseEntity(() -> {
            return this.userService.list(pageDtoIn);
        });
    }

    @Operation(
            summary = "Lấy thông tin user theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            return this.userService.get(id);
        });
    }


    @Operation(
            summary = "Thêm mới user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody @Valid UserDtoIn userDtoIn) {
        return responseEntity(() -> {
            return this.userService.create(userDtoIn);
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseUser.class
                                    )
                            )}
                    )
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody @Valid UpdateUserDtoIn updateUserDtoIn) {
        return responseEntity(() -> {
            return this.userService.update(id, updateUserDtoIn);
        });
    }

    @Operation(
            summary = "Xóa user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                              mediaType = "application/json",
                              schema = @Schema(
                                      implementation = vn.unigap.java.common.response.ApiResponse.class
                              )
                            )}
                    )
            }
    )
    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            this.userService.delete(id);
            return new HashMap<>();
        });
    }

    private static class ResponseUser extends vn.unigap.java.common.response.ApiResponse<UserDtoOut> {
    }

    private static class ResponsePageUser extends vn.unigap.java.common.response.ApiResponse<PageDtoOut<UserDtoOut>> {
    }
}
